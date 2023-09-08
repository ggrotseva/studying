package orm;

import orm.annotations.Column;
import orm.annotations.Entity;
import orm.annotations.Id;
import orm.exceptions.ORMException;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static orm.Connector.getConnection;

public class EntityManager<E> implements DBContext<E> {

    private final Connection connection;

    public EntityManager() throws SQLException {
        this.connection = getConnection();
    }

    @Override
    public boolean persist(E entity) throws SQLException, IllegalAccessException {
        final Field idField = this.getIdField(entity.getClass());
        idField.setAccessible(true);

        Object idValue = idField.get(entity);

        if (idValue == null || Long.parseLong(idValue.toString()) <= 0) {
            boolean isInserted = this.doInsert(entity);

            // assign the id from the database to the id Field of the entity
            setAssignedId(entity, idField);

            return isInserted;
        }

        long primaryKeyValue = (long) idValue;

        return this.doUpdate(entity, primaryKeyValue);
    }

    @Override
    public Iterable<E> find(Class<E> entityType) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        return find(entityType, "");
    }

    @Override
    public Iterable<E> find(Class<E> entityType, String where) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        String tableName = getTableName(entityType);

        String sql = String.format("SELECT * FROM %s %s", tableName, where.equals("") ? "" : "WHERE " + where);

        ResultSet resultSet = this.connection.prepareStatement(sql).executeQuery();

        List<E> resultList = new ArrayList<>();

        // we create entity object for every table entry
        E entity = this.createEntity(entityType, resultSet);

        while (entity != null) {
            resultList.add(entity);
            entity = this.createEntity(entityType, resultSet);
        }

        return resultList;
    }

    @Override
    public E findFirst(Class<E> entityType) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        return findFirst(entityType, "");
    }

    @Override
    public E findFirst(Class<E> entityType, String where) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        String tableName = getTableName(entityType);

        String sql = String.format("SELECT * FROM %s %s LIMIT 1", tableName, where.equals("") ? "" : "WHERE " + where);

        ResultSet resultSet = this.connection.prepareStatement(sql).executeQuery();

        return this.createEntity(entityType, resultSet);
    }

    @Override
    public void handleEntityTable(Class<E> entityType) throws SQLException {
        final String tableName = getTableName(entityType);

        List<String> sqlColumns = getSqlColumnNames(tableName);

        if (sqlColumns.isEmpty()) {
            doCreate(entityType, tableName);
        } else {
            doAlter(entityType, sqlColumns);
        }
    }


    private void doCreate(Class<E> entityType, String tableName) throws SQLException {

        Field idField = getIdField(entityType);

        String idDataType = getSqlColumnType(idField);

        if (!(idDataType.equals("INT") || idDataType.equals("BIGINT"))) {
            throw new ORMException("The Id field of " + entityType + " entity must be an integer type");
        }

        String columnDefinitions = Arrays.stream(entityType.getDeclaredFields())
                .filter(f -> f.isAnnotationPresent(Column.class) && !f.isAnnotationPresent(Id.class))
                .map(f -> f.getAnnotation(Column.class).name() + " " + getSqlColumnType(f))
                .collect(Collectors.joining(", "));

        String sql = String.format("CREATE TABLE %s (id %s PRIMARY KEY AUTO_INCREMENT, %s)", tableName, idDataType, columnDefinitions);

        System.out.println(sql);

        this.connection.prepareStatement(sql).execute();
    }

    private void doAlter(Class<E> entity, List<String> sqlColumns) throws SQLException {

        String addColumnDefinitions = createAddColumnDefinitions(entity, sqlColumns);

        if (addColumnDefinitions.equals("")) return;

        connection.prepareStatement(String.format("ALTER TABLE %s %s", getTableName(entity), addColumnDefinitions))
                .executeUpdate();
    }

    private String createAddColumnDefinitions(Class<E> entity, List<String> sqlColumns) {

        List<Field> entityFields = getAllFieldsWithoutId(entity);

        List<String> addColumnDefinitions = new ArrayList<>();

        for (Field field : entityFields) {
            String columnName = field.getAnnotation(Column.class).name();

            if (sqlColumns.contains(columnName)) continue;

            String sqlColumnType = getSqlColumnType(field);

            addColumnDefinitions.add(String.format("ADD COLUMN %s %s", columnName, sqlColumnType));
        }

        return String.join(", ", addColumnDefinitions);
    }

    private List<String> getSqlColumnNames(String tableName) throws SQLException {

        String sql = String.format("SELECT column_name FROM information_schema.columns WHERE table_schema = '%s' " +
                "AND column_name != 'id' AND table_name = '%s'", Connector.getDatabaseName(), tableName);

        List<String> sqlColumns = new ArrayList<>();

        ResultSet tableColumns = this.connection.prepareStatement(sql).executeQuery();

        while (tableColumns.next()) {
            sqlColumns.add(tableColumns.getString(1));
        }

        return sqlColumns;
    }

    private String getSqlColumnType(Field field) {
        Class<?> fieldType = field.getType();

        if (fieldType == long.class || fieldType == Long.class) {
            return "BIGINT";
        } else if (fieldType == int.class || fieldType == Integer.class) {
            return "INT";
        } else if (fieldType == LocalDate.class) {
            return "DATE";
        } else if (fieldType == String.class) {
            return "VARCHAR(255)";
        }

        throw new ORMException("Unsupported type: " + field.getType());
    }

    private E createEntity(Class<E> entityType, ResultSet resultSet) throws SQLException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        if (!resultSet.next()) {
            return null;
        }

        E entity = entityType.getDeclaredConstructor().newInstance();

        Field[] fields = entity.getClass().getDeclaredFields();

        for (Field field : fields) {
            if (!field.isAnnotationPresent(Column.class)
                    && !field.isAnnotationPresent(Id.class)) {
                continue;
            }

            Column columnAnnotation = field.getAnnotation(Column.class);

            String columnName = columnAnnotation != null ?
                    field.getAnnotation(Column.class).name() :
                    field.getName();

            String value = resultSet.getString(columnName);
            entity = this.fillData(entity, field, value);
        }

        return entity;
    }

    private E fillData(E entity, Field field, String value) throws IllegalAccessException {
        field.setAccessible(true);

        if (field.getType() == long.class || field.getType() == Long.class) {
            field.setLong(entity, Long.parseLong(value));
        } else if (field.getType() == int.class || field.getType() == Integer.class) {
            field.setInt(entity, Integer.parseInt(value));
        } else if (field.getType() == LocalDate.class) {
            field.set(entity, LocalDate.parse(value));
        } else if (field.getType() == String.class) {
            field.set(entity, value);
        } else {
            throw new ORMException("Unsupported type: " + field.getType());
        }

        return entity;
    }

    private void setAssignedId(E entity, Field idField) throws SQLException, IllegalAccessException {
        ResultSet resultSet = this.connection
                .prepareStatement(String.format("SELECT * FROM %s ORDER BY id DESC LIMIT 1", getTableName(entity.getClass())))
                .executeQuery();

        resultSet.next();

        Class<?> idClassType = idField.getType();

        if (idClassType == int.class || idClassType == Integer.class) {
            int assignedId = resultSet.getInt("id");
            idField.set(entity, assignedId);
        } else if (idClassType == long.class || idClassType == Long.class) {
            long assignedId = resultSet.getLong("id");
            idField.set(entity, assignedId);
        }
    }

    private boolean doInsert(E entity) throws SQLException {
        String tableName = this.getTableName(entity.getClass());

        Map<String, String> columnValueMap = getColumnValueMap(entity);

        String fieldList = String.join(", ", columnValueMap.keySet());
        String valueList = String.join(", ", columnValueMap.values());

        String sql = String.format("INSERT INTO %s (%s) VALUES (%s)", tableName, fieldList, valueList);

        return connection.prepareStatement(sql).executeUpdate() > 0;
    }

    private boolean doUpdate(E entity, long id) throws SQLException {
        String tableName = this.getTableName(entity.getClass());

        Map<String, String> columnValueMap = getColumnValueMap(entity);

        String setList = columnValueMap.entrySet().stream()
                .map(e -> String.format("%s = %s", e.getKey(), e.getValue()))
                .collect(Collectors.joining(", "));

        String sql = String.format("UPDATE %s SET %s WHERE id = %d", tableName, setList,
                id);

        return connection.prepareStatement(sql).executeUpdate() > 0;
    }

    private String getTableName(Class<?> entityType) {
        Entity annotation = entityType.getAnnotation(Entity.class);

        if (annotation == null) {
            throw new ORMException("Provided class " + entityType + " does't have Entity annotation");
        }

        return annotation.name();
    }

    private Field getIdField(Class<?> entityType) {
        return Arrays.stream(entityType.getDeclaredFields())
                .filter(f -> f.isAnnotationPresent(Id.class))
                .findFirst()
                .orElseThrow(() -> new ORMException("Entity " + entityType + " doesn't have primary key field"));
    }

    private List<Field> getAllFieldsWithoutId(Class<?> entityType) {
        return Arrays.stream(entityType.getDeclaredFields())
                .filter(f -> !f.isAnnotationPresent(Id.class))
                .collect(Collectors.toList());
    }

    private Map<String, String> getColumnValueMap(E entity) {
        Map<String, String> columnValuePairs = new HashMap<>();

        Arrays.stream(entity.getClass().getDeclaredFields())
                .filter(f -> f.isAnnotationPresent(Column.class))
                .forEach(f -> columnValuePairs.put(f.getAnnotation(Column.class).name(),
                        mapValueToFieldType(f, entity)
                ));

        return columnValuePairs;
    }

    private String mapValueToFieldType(Field field, E entity) {
        field.setAccessible(true);

        Object o = null;

        try {
            o = field.get(entity);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        if (o instanceof String || o instanceof LocalDate) {
            return "'" + o + "'";
        }

        return Objects.requireNonNull(o).toString();
    }
}

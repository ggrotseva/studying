# Custom Mini ORM (for MySql)

This custom ORM is light and basic implementation of ORM framework, working with MySQL. Developed so that I can understand how it works underneath.

## Annotations

#### @Entity
- Use on Classes
- In the **name** field of this annotation you should determine the name of the table in the database

#### @Column
- Use on Fields
- In the **name** field of this annotation you should determine the name of the column in the table

#### @Id
- Use on Fields
- Determines the field of an @Entity annotated class, used as ID in the database table
- You don't need to annotate it with @Column as well

## Connector

**Important!** In the Connector class, you should insert your database name and MySQL server credentials.

## EntityManager

Importing EntityManager class gives you access to the following methods:
- void handleEntityTable(Class\<E> entityClass)
- boolean persist(E entity)
- E findFirst(Class\<E> entityType)
- E findFirst(Class\<E> entityType, String where)
- Iterable\<E> find(Class\<E> entityType)
- Iterable\<E> find(Class\<E> entityType, String where)

```java
import orm.EntityManager;

...

EntityManager<User> entityManager = new EntityManager<>();

User user = new User("Georgi", 28);

// creates or alters the table in the database in update mode
// altered field name or type in entity class results in entirely new column in the table
entityManager.handleEntityTable(User.class);

// persists the object to the database
// performs update if object is already in the database
entityManager.persist(user);

// performs SELECT query for the first entry in the table
User firstUser = entityManager.findFirst(User.class);

// performs SELECT query with WHERE condition and limits to 1 entry
User firstUser = entityManager.findFirst(User.class, "name = 'Georgi'");

// performs SELECT query on the whole database table
Iterable<User> users = entityManager.find(User.class);

// performs SELECT query with WHERE condition on the whole database table 
Iterable<User> users = entityManager.find(User.class, "name = 'Georgi' AND age >= 18");
```

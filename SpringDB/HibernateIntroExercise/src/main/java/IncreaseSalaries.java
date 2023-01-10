import entities.Employee;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;

public class IncreaseSalaries {

    private static final String GET_EMPLOYEES_FROM_DEPARTMENTS =
            "From Employee e where e.department.name IN ('Engineering', 'Tool Design', 'Marketing', 'Information Services')";

    private static final String EMPLOYEE_NAME_SALARY_FORMAT = "%s %s ($%.2f)%n";

    public static void main(String[] args) {
        final EntityManager entityManager = Utils.createEntityManager();

        entityManager.getTransaction().begin();

        final List<Employee> employeeList = entityManager.createQuery(GET_EMPLOYEES_FROM_DEPARTMENTS, Employee.class)
                .getResultList();

        for (Employee employee : employeeList) {
            final BigDecimal newSalary = employee.getSalary().multiply(BigDecimal.valueOf(1.12));
            employee.setSalary(newSalary);
            entityManager.persist(employee);
        }

        entityManager.getTransaction().commit();

        entityManager.createQuery(GET_EMPLOYEES_FROM_DEPARTMENTS, Employee.class)
                .getResultList()
                .forEach(e -> System.out.printf(EMPLOYEE_NAME_SALARY_FORMAT, e.getFirstName(), e.getLastName(), e.getSalary()));

        entityManager.close();
    }
}

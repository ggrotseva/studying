import entities.Employee;

import javax.persistence.EntityManager;

public class EmployeesFromDepartment {

    private static final String DEPARTMENT_NAME = "Research and Development";

    private static final String GET_EMPLOYEES_FROM_DEPARTMENT =
            "SELECT e FROM Employee e WHERE e.department.name = :dp " +
            "ORDER BY e.salary, e.id";

    private static final String PRINT_EMPLOYEE_DEP_SALARY_FORMAT = "%s %s from %s - $%.2f%n";

    public static void main(String[] args) {
        final EntityManager entityManager = Utils.createEntityManager();

        entityManager.createQuery(GET_EMPLOYEES_FROM_DEPARTMENT, Employee.class)
                .setParameter("dp", DEPARTMENT_NAME)
                .getResultList()
                .forEach(e -> System.out.printf(PRINT_EMPLOYEE_DEP_SALARY_FORMAT,
                        e.getFirstName(), e.getLastName(), e.getDepartment().getName(), e.getSalary()));

        entityManager.close();
    }
}

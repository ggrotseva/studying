import entities.Employee;

import javax.persistence.EntityManager;
import java.util.Scanner;

public class FindEmployeesByFirstName {

    private static final String GET_EMPLOYEES_WITH_NAME_LIKE =
            "From Employee e where e.firstName LIKE :prefix";

    private static final String EMPLOYEE_NAME_JOB_SALARY_FORMAT = "%s %s - %s - ($%.2f)%n";

    public static void main(String[] args) {
        final EntityManager entityManager = Utils.createEntityManager();
        final String namePrefix = new Scanner(System.in).nextLine();

        entityManager.getTransaction().begin();

        entityManager.createQuery(GET_EMPLOYEES_WITH_NAME_LIKE, Employee.class)
                .setParameter("prefix", namePrefix + "%")
                .getResultList()
                .forEach(e -> System.out.printf(EMPLOYEE_NAME_JOB_SALARY_FORMAT, e.getFirstName(), e.getLastName(), e.getJobTitle(), e.getSalary()));

        entityManager.getTransaction().commit();
        entityManager.close();
    }
}

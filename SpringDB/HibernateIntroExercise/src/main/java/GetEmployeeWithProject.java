import entities.Employee;

import javax.persistence.EntityManager;
import java.util.Scanner;

public class GetEmployeeWithProject {

    private static final String GET_EMPLOYEE_WITH_PROJECT = "SELECT e FROM Employee e WHERE e.id = :id";

    public static void main(String[] args) {
        final EntityManager entityManager = Utils.createEntityManager();
        final int employeeId = new Scanner(System.in).nextInt();

        final String employeeProjectsFormatted = entityManager.createQuery(GET_EMPLOYEE_WITH_PROJECT, Employee.class)
                .setParameter("id", employeeId)
                .getSingleResult()
                .toStringProjects();

        System.out.println(employeeProjectsFormatted);

        entityManager.close();
    }
}

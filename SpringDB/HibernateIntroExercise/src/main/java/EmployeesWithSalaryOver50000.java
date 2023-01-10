import javax.persistence.EntityManager;

public class EmployeesWithSalaryOver50000 {

    private static final String GET_FIRST_NAME_EMPLOYEE_WHERE_SALARY_OVER_50000 =
            "SELECT firstName FROM Employee WHERE salary > 50000";

    public static void main(String[] args) {
        final EntityManager entityManager = Utils.createEntityManager();

        entityManager.getTransaction().begin();

        entityManager.createQuery(GET_FIRST_NAME_EMPLOYEE_WHERE_SALARY_OVER_50000, String.class)
                .getResultList()
                .forEach(System.out::println);

        entityManager.getTransaction().commit();
        entityManager.close();
    }
}

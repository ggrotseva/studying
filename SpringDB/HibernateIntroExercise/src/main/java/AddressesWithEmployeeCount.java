import entities.Address;

import javax.persistence.EntityManager;

public class AddressesWithEmployeeCount {
    private static final String GET_ADDRESS_WITH_EMPLOYEE_COUNT = "SELECT a FROM Address a ORDER BY a.employees.size DESC";

    public static void main(String[] args) {
        final EntityManager entityManager = Utils.createEntityManager();

        entityManager.createQuery(GET_ADDRESS_WITH_EMPLOYEE_COUNT, Address.class)
                .setMaxResults(10)
                .getResultList()
                .forEach(a -> System.out.println(a.toStringEmployeeCount()));

        entityManager.close();
    }
}

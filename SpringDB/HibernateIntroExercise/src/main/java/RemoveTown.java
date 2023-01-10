import entities.Address;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Scanner;

public class RemoveTown {

    private static final String GET_ADDRESSES_BY_TOWN_NAME = "FROM Address a WHERE a.town.name = :town";

    private static final String DELETE_TOWN = "DELETE FROM Town t WHERE t.name = :town";

    public static void main(String[] args) {
        final EntityManager entityManager = Utils.createEntityManager();
        final String townName = new Scanner(System.in).nextLine();

        final List<Address> addressList = entityManager.createQuery(GET_ADDRESSES_BY_TOWN_NAME, Address.class)
                .setParameter("town", townName)
                .getResultList();

        entityManager.getTransaction().begin();

        // set each Employee's Addresses to null
        addressList.forEach(a -> {
            a.getEmployees().forEach(e -> e.setAddress(null));
            a.setTown(null);
            entityManager.remove(a);
        });

        // important step - flush - syncs the changes so far with the DB
        // or else town cannot be deleted (FK constraint)
        entityManager.flush();

        entityManager.createQuery(DELETE_TOWN).setParameter("town", townName)
                .executeUpdate();

        entityManager.getTransaction().commit();

        System.out.printf("%d %s in %s deleted",
                addressList.size(),
                addressList.size() == 1 ? "address" : "addresses",
                townName);

        entityManager.close();
    }
}
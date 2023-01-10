import entities.Town;

import javax.persistence.*;
import java.util.List;

public class ChangeCasing {

    private static final String GET_ALL_TOWNS = "FROM Town";

    public static void main(String[] args) {

        final EntityManager entityManager = Utils.createEntityManager();

        // open a database connection
        entityManager.getTransaction().begin();

        final TypedQuery<Town> allTownsQuery = entityManager.createQuery(GET_ALL_TOWNS, Town.class);
        final List<Town> towns = allTownsQuery.getResultList();

        for (Town town : towns) {
            final String townName = town.getName();

            if (townName.length() <= 5) {
                town.setName(townName.toUpperCase());
                entityManager.persist(town);
            }
        }

        entityManager.getTransaction().commit();
        // close the database connection
        entityManager.close();
    }
}

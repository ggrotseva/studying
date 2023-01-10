import entities.Project;

import javax.persistence.EntityManager;

public class FindLatest10Projects {

    private static final String GET_LAST_PROJECTS = "SELECT p FROM Project p ORDER BY startDate DESC";

    public static void main(String[] args) {
        final EntityManager entityManager = Utils.createEntityManager();

        entityManager.createQuery(GET_LAST_PROJECTS, Project.class)
                .setMaxResults(10)
                .getResultList()
                .forEach(p -> System.out.println(p.toFormatString()));

        entityManager.close();
    }
}

import javax.persistence.EntityManager;
import java.util.Scanner;

public class ContainsEmployee {

    private static final String GET_EMPLOYEE_WITH_NAMES = "SELECT COUNT(e) FROM Employee e WHERE firstName = :fn AND lastName = :ln";

    public static void main(String[] args) {
        final EntityManager entityManager = Utils.createEntityManager();

        final Scanner scan = new Scanner(System.in);
        final String[] name = scan.nextLine().split(" ");

        final String firstName = name[0];
        final String lastName = name[1];

        Long foundName = entityManager.createQuery(GET_EMPLOYEE_WITH_NAMES, Long.class)
                .setParameter("fn", firstName)
                .setParameter("ln", lastName)
                .getSingleResult();

        if (foundName == 0) {
            System.out.println("No");
        } else {
            System.out.println("Yes");
        }

        entityManager.close();
    }
}

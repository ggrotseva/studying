import entities.Address;
import entities.Employee;
import entities.Town;

import javax.persistence.EntityManager;
import java.util.Scanner;
import java.util.Set;

public class AddNewAddressAndUpdateEmployee {

    private static final String NEW_ADDRESS_VITOSHKA = "Vitoshka 15";

    private static final String GET_TOWN_SOFIA = "select t from Town t where t.name = 'Sofia'";

    private static final String SET_EMPLOYEE_NEW_ADDRESS = "UPDATE Employee e SET e.address = :newAd WHERE e.lastName = :ln";

    public static void main(String[] args) {
        final EntityManager entityManager = Utils.createEntityManager();

        final String employeeLastName = new Scanner(System.in).nextLine();

        entityManager.getTransaction().begin();

        // persist the new Address entity
        Address newAddress = new Address();
        newAddress.setText(NEW_ADDRESS_VITOSHKA);

        // setting city so that it isn't null /or else next task comes with exception
        try {
            Town townOfSofia = entityManager.createQuery(GET_TOWN_SOFIA, Town.class).getSingleResult();
            newAddress.setTown(townOfSofia);
        } catch (Exception ignore) {
        }

        entityManager.persist(newAddress);

        // update Employee with the new Address
        int affectedRows = entityManager.createQuery(SET_EMPLOYEE_NEW_ADDRESS)
                .setParameter("newAd", newAddress)
                .setParameter("ln", employeeLastName)
                .executeUpdate();

        if (affectedRows > 0) {
            entityManager.getTransaction().commit();
        } else {
            entityManager.getTransaction().rollback();
        }

        entityManager.close();
    }
}

import entities.MagicWand;
import entities.Wizard;
import entities.WizardDeposit;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("soft_uni");

        EntityManager entityManager = emf.createEntityManager();

        entityManager.getTransaction().begin();

//        Wizard wizard = new Wizard();
//        wizard.setFirstName("Harry");
//        wizard.setLastName("Potter");
//
//        MagicWand mw = new MagicWand();
//        mw.setCreator("Olivander");
//        entityManager.persist(mw);
//
//        wizard.setMagicWand(mw);
// //       mw.setWizard(wizard);
//
//        entityManager.persist(wizard);


//        Wizard wizard2 = new Wizard();
//        wizard2.setFirstName("Ron");
//        wizard2.setLastName("Weasley");

        // deposit adding
        WizardDeposit deposit1 = new WizardDeposit();

        Wizard harry = entityManager.find(Wizard.class, 1L);
        deposit1.setWizard(harry);
        entityManager.persist(deposit1);

//        MagicWand wand2 = new MagicWand();
//        wand2.setCreator("Stackhouse");
//        wand2.setSize((short) 30);


        entityManager.getTransaction().commit();

        entityManager.close();
    }
}

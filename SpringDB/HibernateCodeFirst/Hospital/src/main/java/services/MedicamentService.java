package services;

import entities.Medicament;
import entities.Patient;

import java.util.List;
import java.util.Scanner;

public class MedicamentService extends Service {

    @Override
    public void add(Scanner scanner) {
        System.out.print("Add Medicament:\n" +
                "Enter id of patient for adding a medicament: ");

        Long id = Long.parseLong(scanner.nextLine());

        Patient foundPatient = entityManager.find(Patient.class, id);

        if (foundPatient == null) {
            System.out.println("No such patient. Add Patient to proceed.");
            return;
        }

        System.out.print("Name: ");
        String name = scanner.nextLine();

        Medicament medicament = new Medicament(name);

        entityManager.getTransaction().begin();
        entityManager.persist(medicament);
        foundPatient.addMedicament(medicament);
        entityManager.flush();
        entityManager.getTransaction().commit();


        System.out.println("Medicament added to patient with id " + foundPatient.getId());
    }

    @Override
    public void delete(Scanner scanner) {
        System.out.print("Enter valid id of medicament: ");

        Long id = Long.parseLong(scanner.nextLine());

        Medicament foundMedicament = entityManager.find(Medicament.class, id);

        if (foundMedicament == null) {
            System.out.println("No such medicament");
        } else {
            Long medicamentId = foundMedicament.getId();
            entityManager.getTransaction().begin();

            entityManager.createQuery("delete from Medicament where id=:id")
                    .setParameter("id", medicamentId)
                    .executeUpdate();

            entityManager.getTransaction().commit();

            System.out.print("Successfully deleted medicament with id " + medicamentId);
        }
    }

    @Override
    public void view(Scanner scanner) {
        System.out.print("Enter valid id of medicament: ");
        Long id = Long.parseLong(scanner.nextLine());

        Medicament foundMedicament = entityManager.find(Medicament.class, id);

        if (foundMedicament == null) {
            System.out.println("No such medicament");
        } else {
            System.out.println(foundMedicament);
        }
    }

    @Override
    public void viewAll() {
        List<Medicament> medicaments = entityManager.createQuery("select m from Medicament m", Medicament.class).getResultList();

        if (medicaments == null || medicaments.size() == 0) {
            System.out.println("No medicaments.");
        } else {
            medicaments.forEach(System.out::println);
        }
    }
}

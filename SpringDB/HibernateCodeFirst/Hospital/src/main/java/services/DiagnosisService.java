package services;

import entities.Diagnosis;
import entities.Patient;
import exceptions.RequiredDataException;

import java.util.List;
import java.util.Scanner;

public class DiagnosisService extends Service {

    @Override
    public void add(Scanner scanner) {
        System.out.print("Add Diagnosis:\n" +
                "Enter id of patient for adding a diagnosis: ");

        Long id = Long.parseLong(scanner.nextLine());

        Patient foundPatient = entityManager.find(Patient.class, id);

        if (foundPatient == null) {
            System.out.println("No such patient. Add Patient to proceed.");
            return;
        }

        System.out.print("Name: ");
        String name = scanner.nextLine();
        if (name.isBlank()) {
            throw new RequiredDataException("Diagnosis name cannot be empty");
        }

        Diagnosis diagnosis = new Diagnosis(name);

        System.out.print("Comments (optional): ");
        String comments = scanner.nextLine();

        if (!comments.isBlank()) {
            diagnosis.setComments(comments);
        }

        entityManager.getTransaction().begin();
        entityManager.persist(diagnosis);
        foundPatient.addDiagnosis(diagnosis);
        entityManager.flush();
        entityManager.getTransaction().commit();

        System.out.println("Diagnosis added to patient with id " + foundPatient.getId());
    }

    @Override
    public void delete(Scanner scanner) {
        System.out.print("Enter valid id of diagnosis: ");
        Long id = Long.parseLong(scanner.nextLine());

        Diagnosis foundDiagnosis = entityManager.find(Diagnosis.class, id);

        if (foundDiagnosis == null) {
            System.out.println("No such diagnosis");
        } else {

            Long diagnosisId = foundDiagnosis.getId();
            entityManager.getTransaction().begin();
            entityManager.createQuery("delete from Diagnosis where id=:id")
                    .setParameter("id", diagnosisId)
                    .executeUpdate();

            entityManager.getTransaction().commit();

            System.out.print("Successfully deleted diagnosis with id " + diagnosisId);
        }
    }

    @Override
    public void view(Scanner scanner) {
        System.out.print("Enter valid id of diagnosis: ");

        Long id = Long.parseLong(scanner.nextLine());

        Diagnosis foundDiagnosis = entityManager.find(Diagnosis.class, id);
        if (foundDiagnosis != null) {
            System.out.println(foundDiagnosis);
        } else {
            System.out.println("No such diagnosis");
        }

    }

    @Override
    public void viewAll() {
        List<Diagnosis> diagnoses = entityManager.createQuery("select d from Diagnosis d", Diagnosis.class).getResultList();

        if (diagnoses == null || diagnoses.size() == 0) {
            System.out.println("No diagnoses.");
        } else {
            diagnoses.forEach(System.out::println);
        }
    }
}

package services;

import entities.Medicament;
import entities.Patient;
import entities.Visitation;
import exceptions.RequiredDataException;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class VisitationService extends Service {

    @Override
    public void add(Scanner scanner) {
        System.out.print("Add Visitation:\n" +
                "Enter id of patient for adding a visitation: ");

        Long id = Long.parseLong(scanner.nextLine());

        Patient foundPatient = entityManager.find(Patient.class, id);

        if (foundPatient == null) {
            System.out.print("No such patient. Add Patient to proceed.");
            return;
        }

        System.out.print("Date (in numeric format: DD.MM.YYYY): ");
        LocalDate date = createDate(scanner);

        Visitation visitation = new Visitation(date);

        System.out.print("Comments (optional): ");
        String comments = scanner.nextLine();

        if (!comments.isBlank()) {
            visitation.setComments(comments);
        }

        entityManager.getTransaction().begin();
        entityManager.persist(visitation);
        foundPatient.addVisitation(visitation);
        entityManager.flush();
        entityManager.getTransaction().commit();


        System.out.println("Visitation added to patient with id " + foundPatient.getId());
    }

    @Override
    public void delete(Scanner scanner) {
        System.out.print("Enter valid id of patient: ");

        Long patientId = Long.parseLong(scanner.nextLine());

        Patient foundPatient = entityManager.find(Patient.class, patientId);

        if (foundPatient == null) {
            System.out.println("No such patient.");
        }

        if (foundPatient.getVisitations() != null) {
            foundPatient.getVisitations().forEach(System.out::println);
            System.out.print("Enter the id of visitation you want to delete: ");

            Long visitationId = Long.parseLong(scanner.nextLine());
            Visitation foundVisitation = entityManager.find(Visitation.class, visitationId);

            entityManager.getTransaction().begin();

            foundPatient.removeVisitation(foundVisitation);
            entityManager.flush();

            entityManager.createQuery("delete from Visitation where id=:id")
                    .setParameter("id", visitationId)
                    .executeUpdate();

            entityManager.getTransaction().commit();

            System.out.println("Visitation with id " + visitationId + " successfully deleted.");
        }
    }

    @Override
    public void view(Scanner scanner) {
        System.out.print("Enter valid id of visitation: ");
        Long id = Long.parseLong(scanner.nextLine());

        Visitation foundVisitation = entityManager.find(Visitation.class, id);

        if (foundVisitation == null) {
            System.out.println("No such visitation");
        } else {
            System.out.println(foundVisitation);
        }
    }

    @Override
    public void viewAll() {
        List<Visitation> visitations = entityManager.createQuery("select v from Visitation v", Visitation.class).getResultList();

        if (visitations == null || visitations.size() == 0) {
            System.out.println("No visitations.");
        } else {
            visitations.forEach(System.out::println);
        }
    }

    private LocalDate createDate(Scanner scanner) {
        String dateInfo = scanner.nextLine();

        if (dateInfo.isBlank()) {
            throw new RequiredDataException("Visitation date cannot be empty");
        }

        String[] dateTokens = dateInfo.split("\\.");

        int day = Integer.parseInt(dateTokens[0]);
        int month = Integer.parseInt(dateTokens[1]);
        int year = Integer.parseInt(dateTokens[2]);

        LocalDate date = null;

        try {
            date = LocalDate.of(year, month, day);
        } catch (DateTimeException e) {
            System.out.print("Invalid date. Please try again: ");
            date = createDate(scanner);
        }

        return date;
    }
}

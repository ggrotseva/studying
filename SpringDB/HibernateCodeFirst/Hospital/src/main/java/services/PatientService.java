package services;

import entities.Patient;
import exceptions.RequiredDataException;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class PatientService extends Service {

    @Override
    public void add(Scanner scanner) {
        System.out.print("Add Patient:\nFirst name: ");
        String firstName = scanner.nextLine();
        if (firstName.isBlank()) {
            throw new RequiredDataException("First name cannot be empty");
        }

        System.out.print("Last name: ");
        String lastName = scanner.nextLine();
        if (lastName.isBlank()) {
            throw new RequiredDataException("Last name cannot be empty");
        }

        System.out.print("Address: ");
        String address = scanner.nextLine();

        System.out.print("Birthdate (in numeric format: DD.MM.YYYY): ");
        LocalDate birthdate = createBirthdate(scanner);

        Patient patient = new Patient(firstName, lastName, address, birthdate);

        System.out.print("Email (optional): ");
        String email = scanner.nextLine();

        if (!email.isBlank()) {
            if (!email.trim().matches("^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9.-]+$")) {
                System.out.println("Invalid email address. No email is being set");
            } else {
                patient.setEmail(email);
            }
        }

        System.out.print("Insurance(yes/no) (optional): ");
        String hasInsurance = scanner.nextLine();

        if (!hasInsurance.equalsIgnoreCase("yes") && !hasInsurance.equalsIgnoreCase("no")) {
            patient.setHasInsurance(hasInsurance.equalsIgnoreCase("yes"));
        }

        entityManager.getTransaction().begin();
        entityManager.persist(patient);
        entityManager.getTransaction().commit();

        System.out.println("Patient added!");
    }

    @Override
    public void delete(Scanner scanner) {
        System.out.print("Enter valid id of patient: ");
        Long id = Long.parseLong(scanner.nextLine());

        Patient foundPatient = entityManager.find(Patient.class, id);
        if (foundPatient == null) {
            System.out.println("No such patient");
        } else {

            Long patientId = foundPatient.getId();
            entityManager.getTransaction().begin();
            entityManager.createQuery("delete from Patient where id=:id")
                    .setParameter("id", patientId)
                    .executeUpdate();

            entityManager.getTransaction().commit();

            System.out.print("Successfully deleted patient with id " + patientId);
        }
    }

    @Override
    public void view(Scanner scanner) {
        System.out.print("Enter valid id of patient: ");
        Long id = Long.parseLong(scanner.nextLine());

        Patient foundPatient = entityManager.find(Patient.class, id);

        if (foundPatient == null) {
            System.out.println("No such patient");
        } else {
            System.out.println(foundPatient);
            System.out.print("If you want to view patient's information, choose from VISITATIONS, MEDICAMENTS, DIAGNOSES: ");

            viewAdditionalInformation(scanner.nextLine(), foundPatient);
        }

    }

    @Override
    public void viewAll() {
        List<Patient> patients = entityManager.createQuery("select p from Patient p", Patient.class).getResultList();

        if (patients == null || patients.size() == 0) {
            System.out.println("No patients.");
        } else {
            patients.forEach(System.out::println);
        }
    }

    private LocalDate createBirthdate(Scanner scanner) {
        String birthDateInfo = scanner.nextLine();

        if (birthDateInfo.isBlank()) {
            throw new RequiredDataException("Birth date cannot be empty");
        }

        String[] birthDateTokens = birthDateInfo.split("\\.");

        int day = Integer.parseInt(birthDateTokens[0]);
        int month = Integer.parseInt(birthDateTokens[1]);
        int year = Integer.parseInt(birthDateTokens[2]);

        LocalDate birthDate = null;

        try {
            birthDate = LocalDate.of(year, month, day);
        } catch (DateTimeException e) {
            System.out.print("Invalid date. Please try again: ");
            birthDate = createBirthdate(scanner);
        }

        if (birthDate.isAfter(LocalDate.now())) {
            System.out.print("Invalid birth date. Please try again: ");
            birthDate = createBirthdate(scanner);
        }

        return birthDate;
    }

    private void viewAdditionalInformation(String input, Patient patient) {
        if (input.equalsIgnoreCase("visitations")) {
            patient.getVisitations().forEach(System.out::println);
        } else if (input.equalsIgnoreCase("medicaments")) {
            patient.getPrescribedMedicaments().forEach(System.out::println);
        } else if (input.equalsIgnoreCase("diagnoses")) {
            patient.getDiagnoses().forEach(System.out::println);
        }
    }

}

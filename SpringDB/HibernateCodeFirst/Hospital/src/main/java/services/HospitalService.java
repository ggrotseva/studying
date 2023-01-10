package services;

import java.util.Scanner;

public class HospitalService {

    private final MedicamentService medicamentService;
    private final PatientService patientService;
    private final DiagnosisService diagnosisService;
    private final VisitationService visitationService;

    public HospitalService() {
        this.medicamentService = new MedicamentService();
        this.patientService = new PatientService();
        this.diagnosisService = new DiagnosisService();
        this.visitationService = new VisitationService();
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Choose action from VIEW, VIEW ALL, ADD, DELETE, EXIT: ");
        readInput(scanner);
    }

    private void readInput(Scanner scanner) {
        String action = scanner.nextLine();

        if (action.trim().equalsIgnoreCase("view")) {
            System.out.print("Choose what kind of information you want to VIEW from MEDICAMENT, DIAGNOSIS, PATIENT, VISITATION: ");
            Service service = chooseService(scanner);
            service.view(scanner);

        } else if (action.trim().equalsIgnoreCase("view all")) {
            System.out.print("Choose what kind of information you want to VIEW from MEDICAMENT, DIAGNOSIS, PATIENT, VISITATION: ");
            Service service = chooseService(scanner);
            service.viewAll();

        } else if (action.trim().equalsIgnoreCase("add")) {
            System.out.print("Choose what kind of information you want to ADD from MEDICAMENT, DIAGNOSIS, PATIENT, VISITATION: ");
            Service service = chooseService(scanner);
            service.add(scanner);

        } else if (action.trim().equalsIgnoreCase("delete")) {
            System.out.print("Choose what kind of information you want to DELETE from MEDICAMENT, DIAGNOSIS, PATIENT, VISITATION: ");
            Service service = chooseService(scanner);
            service.delete(scanner);

        } else if (action.trim().equalsIgnoreCase("exit")) {
            closeEntityManagers();
            System.exit(0);
        } else {
            System.out.print("Please write a valid option VIEW, VIEW ALL, ADD, DELETE, EXIT: ");
            readInput(scanner);
        }

        run();
    }

    private Service chooseService(Scanner scanner) {
        String entity = scanner.nextLine();

        if (entity.trim().equalsIgnoreCase("patient")) {
            return this.patientService;
        } else if (entity.trim().equalsIgnoreCase("visitation")) {
            return this.visitationService;
        } else if (entity.trim().equalsIgnoreCase("medicament")) {
            return this.medicamentService;
        } else if (entity.trim().equalsIgnoreCase("diagnosis")) {
            return this.diagnosisService;
        } else if (entity.trim().equalsIgnoreCase("exit")) {
            closeEntityManagers();
            System.exit(0);
        }

        System.out.print("Please write a valid option MEDICAMENT, DIAGNOSIS, PATIENT, VISITATION or EXIT to exit the program: ");
        return chooseService(scanner);
    }

    private void closeEntityManagers() {
        this.patientService.closeEntityManager();
        this.visitationService.closeEntityManager();
        this.medicamentService.closeEntityManager();
        this.diagnosisService.closeEntityManager();
    }

}
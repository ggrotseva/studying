package entities;

import jakarta.persistence.*;

import java.sql.Blob;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "patients")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String address;

    @Column
    private String email;

    @Column(name = "birth_date", nullable = false, columnDefinition = "date")
    private LocalDate birthDate;

    @Column(columnDefinition = "blob")
    private Blob picture;

    @Column(name = "has_insurance")
    private Boolean hasInsurance;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "patients_visitations",
            joinColumns = @JoinColumn(name = "patient_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "visitation_id", referencedColumnName = "id"))
    private List<Visitation> visitations;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "patients_diagnoses",
            joinColumns = @JoinColumn(name = "patient_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "diagnosis_id", referencedColumnName = "id"))
    private List<Diagnosis> diagnoses;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "patients_medicaments",
            joinColumns = @JoinColumn(name = "patient_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "medicament_id", referencedColumnName = "id"))
    private List<Medicament> prescribedMedicaments;


    public Patient() {
        this.visitations = new ArrayList<>();
        this.diagnoses = new ArrayList<>();
        this.prescribedMedicaments = new ArrayList<>();
    }

    public Patient(String firstName, String lastName, String address, LocalDate birthDate) {
        this();

        setFirstName(firstName);
        setLastName(lastName);
        setAddress(address);
        setBirthDate(birthDate);
    }


    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Boolean getHasInsurance() {
        return hasInsurance;
    }

    public void setHasInsurance(Boolean hasInsurance) {
        this.hasInsurance = hasInsurance;
    }

    public List<Visitation> getVisitations() {
        return Collections.unmodifiableList(this.visitations);
    }

    public void addVisitation(Visitation visitation) {
        this.visitations.add(visitation);
    }

    public void removeVisitation(Visitation visitation) {
        this.visitations.remove(visitation);
    }

    public List<Diagnosis> getDiagnoses() {
        return Collections.unmodifiableList(this.diagnoses);
    }

    public void addDiagnosis(Diagnosis diagnosis) {
        this.diagnoses.add(diagnosis);
    }

    public void removeDiagnosis(Diagnosis diagnosis) {
        this.diagnoses.remove(diagnosis);
    }

    public List<Medicament> getPrescribedMedicaments() {
        return Collections.unmodifiableList(this.prescribedMedicaments);
    }

    public void addMedicament(Medicament medicament) {
        this.prescribedMedicaments.add(medicament);
    }

    public void removeMedicament(Medicament medicament) {
        this.prescribedMedicaments.remove(medicament);
    }


    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();

        output.append("Patient: id=").append(id)
                .append(", First Name: ").append(firstName)
                .append(", Last Name: ").append(lastName)
                .append(", Address: ").append(address)
                .append(", Birthdate: ").append(birthDate)
                .append(email != null ? ", Email: " + email : "");

        if (hasInsurance != null) {
            output.append(", has insurance: ").append(hasInsurance);
        }

        return output.toString();
    }
}

package entities;

import jakarta.persistence.*;

@Entity
@Table(name = "medicaments")
public class Medicament {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String name;

    public Medicament() {
    }

    public Medicament(String name) {
        setName(name);
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();

        output.append("Medicament: id=").append(id)
                .append(", Name: ").append(name);

        return output.toString();
    }
}

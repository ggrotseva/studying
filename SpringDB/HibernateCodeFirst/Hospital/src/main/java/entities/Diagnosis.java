package entities;

import jakarta.persistence.*;

@Entity
@Table(name = "diagnoses")
public class Diagnosis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "Text")
    private String comments;

    public Diagnosis() {
    }

    public Diagnosis(String name) {
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

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();

        output.append("Diagnosis: id=").append(id)
                .append(", Name: ").append(name)
                .append(comments != null ? ", Comments: " + comments : "");

        return output.toString();
    }
}

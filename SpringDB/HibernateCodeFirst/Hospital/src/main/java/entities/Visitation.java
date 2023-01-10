package entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "visitations")
public class Visitation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private LocalDate date;

    @Column(columnDefinition = "Text")
    private String comments;

    public Visitation() {
    }

    public Visitation(LocalDate date) {
        setDate(date);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
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

        output.append("Visitation: id=").append(id)
                .append(", Date: ").append(date)
                .append(comments != null ? ", Comments: " + comments : "");

        return output.toString();
    }
}

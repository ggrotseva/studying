package hasEntities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @ManyToMany(targetEntity = Article.class, mappedBy = "categories")
    private List<Article> articles;

    public Category() {
        this.articles = new ArrayList<>();
    }
}

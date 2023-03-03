package softuni.expirationManager.model;

import jakarta.persistence.*;

@Entity
@Table(name = "recipes")
public class RecipeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


}

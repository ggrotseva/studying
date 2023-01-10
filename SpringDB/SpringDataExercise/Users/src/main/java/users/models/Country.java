package users.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "countries")
public class Country extends BaseEntity  {

    @Column
    private String name;

    @OneToMany(mappedBy = "country")
    List<Town> towns;

    public Country() {
    }
}

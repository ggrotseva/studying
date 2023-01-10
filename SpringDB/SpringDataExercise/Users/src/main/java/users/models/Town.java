package users.models;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "towns")
public class Town extends BaseEntity {

    @Column
    private String name;

    @ManyToOne
    @JoinColumn(name = "country_id", referencedColumnName = "id")
    private Country country;

    @OneToMany(mappedBy = "bornTown")
    private List<User> born;

    @OneToMany(mappedBy = "currentTown")
    private List<User> current;

    public Town() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public List<User> getBorn() {
        return born;
    }

    public void setBorn(List<User> born) {
        this.born = born;
    }

    public List<User> getCurrent() {
        return current;
    }

    public void setCurrent(List<User> current) {
        this.current = current;
    }

    // --------------------------------------------------------------------------------------------------------

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        public Town town;

        private Builder() {
            this.town = new Town();
        }

        public Builder withName(String name) {
            town.setName(name);
            return this;
        }

        public Builder withCountry(Country country) {
            town.setCountry(country);
            return this;
        }

        public Town build() {
            return town;
        }
    }
}

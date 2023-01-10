package softuni.exam.models.dto;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class TownImportDTO {

    @Length(min = 2)
    @NotNull
    private String townName;

    @Min(1)
    @NotNull
    private long population;

    public TownImportDTO() {
    }

    public String getTownName() {
        return townName;
    }

    public long getPopulation() {
        return population;
    }

    public void setTownName(String townName) {
        this.townName = townName;
    }

    public void setPopulation(long population) {
        this.population = population;
    }
}

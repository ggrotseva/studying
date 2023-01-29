package softuni.exam.models.dto;

import org.hibernate.validator.constraints.Length;

public class CountryImportDTO {

    @Length(min = 2, max = 60)
    private String countryName;

    @Length(min = 2, max = 20)
    private String currency;

    public CountryImportDTO() {
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}

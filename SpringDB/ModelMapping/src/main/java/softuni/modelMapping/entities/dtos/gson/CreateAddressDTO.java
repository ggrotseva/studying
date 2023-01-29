package softuni.modelMapping.entities.dtos.gson;

import com.google.gson.annotations.Expose;

public class CreateAddressDTO {

    @Expose
    private String country;

    @Expose
    private String city;

    public CreateAddressDTO() {
    }

    public CreateAddressDTO(String country, String city) {
        this.country = country;
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }


}

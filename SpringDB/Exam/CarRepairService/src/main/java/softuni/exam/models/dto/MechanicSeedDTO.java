package softuni.exam.models.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

public class MechanicSeedDTO {

    @Email(regexp = "^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$")
    private String email;

    @Size(min = 2)
    private String firstName;

    @Size(min = 2)
    private String lastName;

    @Size(min = 2)
    private String phone;

    public MechanicSeedDTO() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}

package softuni.modelMapping.entities.dtos;

import com.google.gson.annotations.Expose;
import softuni.modelMapping.entities.dtos.address.CreateAddressDTO;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CreateEmployeeDTO {

    @Expose
    private String firstName;

//    private String lastName;

    @Expose
    private BigDecimal salary;

    @Expose
    private LocalDate birthday;

    @Expose
    private CreateAddressDTO address;

    public CreateEmployeeDTO(String firstName, BigDecimal salary, LocalDate birthday, CreateAddressDTO address) {
        this.firstName = firstName;
//        this.lastName = lastName;
        this.salary = salary;
        this.birthday = birthday;
        this.address = address;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

//    public String getLastName() {
//        return lastName;
//    }

//    public void setLastName(String lastName) {
//        this.lastName = lastName;
//    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public CreateAddressDTO getAddress() {
        return address;
    }

    public void setAddress(CreateAddressDTO address) {
        this.address = address;
    }

}

package carDealer.domain.customers;

import java.time.LocalDate;

public class CustomerImportDTO {

    private String name;
    private LocalDate birthDate;
    private boolean isYoungDriver;

    public CustomerImportDTO() {
    }

    public String getName() {
        return name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public boolean isYoungDriver() {
        return isYoungDriver;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public void setYoungDriver(boolean youngDriver) {
        isYoungDriver = youngDriver;
    }
}

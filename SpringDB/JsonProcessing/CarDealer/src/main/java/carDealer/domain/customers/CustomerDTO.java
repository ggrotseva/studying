package carDealer.domain.customers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CustomerDTO {

    private Long id;
    private String name;
    private LocalDate birthDate;
    private boolean isYoungDriver;
    private List<String> sales;

    public CustomerDTO() {
        sales = new ArrayList<>();
    }

    public void setId(Long id) {
        this.id = id;
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

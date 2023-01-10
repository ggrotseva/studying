package softuni.modelMapping.entities.dtos;

import com.google.gson.annotations.Expose;

import java.util.List;

public class CompanyDTO {

    @Expose
    private String name;

    @Expose
    private List<CreateEmployeeDTO> employees;

    public CompanyDTO(String name, List<CreateEmployeeDTO> employees) {
        this.name = name;
        this.employees = employees;
    }
}

package softuni.modelMapping.services;

import softuni.modelMapping.entities.dtos.CreateEmployeeDTO;
import softuni.modelMapping.entities.dtos.EmployeeDTO;
import softuni.modelMapping.entities.Employee;

import java.util.List;

public interface EmployeeService {

    Employee create(CreateEmployeeDTO employeeDTO);

    List<EmployeeDTO> findAll();
}

package softuni.modelMapping;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import softuni.modelMapping.entities.dtos.EmployeeDTO;
import softuni.modelMapping.entities.Address;
import softuni.modelMapping.entities.Employee;

import java.math.BigDecimal;
import java.time.Instant;

//@Component
public class ModelMapperMain implements CommandLineRunner {


    @Override
    public void run(String... args) throws Exception {
        ModelMapper mapper = new ModelMapper();

//        PropertyMap<Employee, EmployeeDTO> propertyMap = new PropertyMap<Employee, EmployeeDTO>() {
//            @Override
//            protected void configure() {
//                map().setCity(source.getAddress().getCity());
//            }
//        };
//        mapper.addMappings(propertyMap);

//        TypeMap<Employee, EmployeeDTO> typeMap = mapper.createTypeMap(Employee.class, EmployeeDTO.class);

//        typeMap.addMappings(mapping -> mapping.map(
//                source -> source.getAddress().getCity(),
//                EmployeeDTO::setAddressCity));
//        mapper.validate();


        Address address = new Address("Bulgaria", "Plovdiv");
        Employee employee = new Employee("Name", BigDecimal.TEN, address);

        EmployeeDTO employeeDTO = mapper.map(employee, EmployeeDTO.class);

        System.out.println(employeeDTO);
    }
}

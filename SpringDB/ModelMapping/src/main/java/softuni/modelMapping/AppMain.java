package softuni.modelMapping;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import softuni.modelMapping.entities.dtos.address.AddressDTO;
import softuni.modelMapping.entities.dtos.address.CreateAddressDTO;
import softuni.modelMapping.entities.dtos.CreateEmployeeDTO;
import softuni.modelMapping.entities.Employee;
import softuni.modelMapping.services.AddressService;
import softuni.modelMapping.services.EmployeeService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Scanner;

//@Component
public class AppMain implements CommandLineRunner {

    private final AddressService addressService;
    private final EmployeeService employeeService;
    private final Scanner scan;
    private final Gson gson;


    @Autowired
    public AppMain(AddressService addressService, EmployeeService employeeService, Scanner scan, Gson gson) {
        this.addressService = addressService;
        this.employeeService = employeeService;
        this.scan = scan;
        this.gson = gson;
    }

    @Override
    public void run(String... args) throws Exception {

        createAddress();
//        createEmployee();
//        printAllEmployees();
    }

    private void printAllEmployees() {

        this.employeeService.findAll().forEach(System.out::println);
    }


    private void createEmployee() {
        String firstName = scan.nextLine();
//        String lastName = scan.nextLine();
        BigDecimal salary = new BigDecimal(scan.nextLine());
        LocalDate birthday = LocalDate.parse(scan.nextLine()); // format 2022-09-25

//        long addressId = Long.parseLong(scan.nextLine());

        String country = scan.nextLine();
        String city = scan.nextLine();

        CreateAddressDTO address = new CreateAddressDTO(country, city);

        CreateEmployeeDTO employeeDTO = new CreateEmployeeDTO(firstName, salary, birthday, address);

        Employee employee = this.employeeService.create(employeeDTO);
        System.out.println(employee);

    }

    private void createAddress() {
        
        // starts with JSON and ends with JSON
        String input = scan.nextLine();

        CreateAddressDTO data = this.gson.fromJson(input, CreateAddressDTO.class);

        AddressDTO address = this.addressService.create(data);

        System.out.println(this.gson.toJson(address));
    }
}

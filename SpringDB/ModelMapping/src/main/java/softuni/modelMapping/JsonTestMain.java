package softuni.modelMapping;

import com.google.gson.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import softuni.modelMapping.entities.dtos.gson.CreateAddressDTO;
import softuni.modelMapping.entities.dtos.CompanyDTO;
import softuni.modelMapping.entities.dtos.CreateEmployeeDTO;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

//@Component
public class JsonTestMain implements CommandLineRunner {

    // ако искаме в посока object -> JSON
    private class LocalDateAdapter implements JsonSerializer<LocalDate> {
        @Override
        public JsonElement serialize(LocalDate date, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(date.format(DateTimeFormatter.ISO_LOCAL_DATE));
        }
    }

    // ако искаме в посока JSON -> object
    private class LocalDateDeserializer implements JsonDeserializer<LocalDate> {
        @Override
        public LocalDate deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            return LocalDate.parse(json.toString(), DateTimeFormatter.ISO_LOCAL_DATE);
        }
    }

    private final Scanner scanner;
    private final Gson gson;

    @Autowired
    public JsonTestMain(Scanner scanner) {
        this.scanner = scanner;

        this.gson = new GsonBuilder()
//                .setPrettyPrinting()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .registerTypeAdapter(LocalDate.class, new LocalDateDeserializer())
                .excludeFieldsWithoutExposeAnnotation()
                .create();
    }

    @Override
    public void run(String... args) {
        test();

    }

    private void nestedTest() {
        CreateAddressDTO addressDTO1 = new CreateAddressDTO("Bulgaria", "Burgas");
        CreateEmployeeDTO employee1 = new CreateEmployeeDTO("First", BigDecimal.TEN, LocalDate.now(), addressDTO1);

        CreateAddressDTO addressDTO2 = new CreateAddressDTO("Bulgaria", "Ruse");
        CreateEmployeeDTO employee2 = new CreateEmployeeDTO("Second", BigDecimal.TEN, LocalDate.now(), addressDTO2);

        CreateAddressDTO addressDTO3 = new CreateAddressDTO("Bulgaria", "Varna");
        CreateEmployeeDTO employee3 = new CreateEmployeeDTO("Third", BigDecimal.TEN, LocalDate.now(), addressDTO3);

        CompanyDTO company = new CompanyDTO("Mega", List.of(employee1, employee2, employee3));

        String output = gson.toJson(company);

        System.out.println(output);

        CompanyDTO companyDTO = gson.fromJson(scanner.nextLine(), CompanyDTO.class);

        System.out.println();
    }

    private void test() {

        CreateAddressDTO addressDTO1 = new CreateAddressDTO("Bulgaria", "Burgas");

        // from List to JSON
//        AddressDTO addressDTO2 = new AddressDTO("Bulgaria", "Ruse");
//        AddressDTO addressDTO3 = new AddressDTO("Bulgaria", "Varna");
//
//        List<AddressDTO> list = List.of(addressDTO1, addressDTO2, addressDTO3);
//
//        String output = gson.toJson(list);
//        System.out.println(output);

        // from JSON to List
//        String listJson = this.scanner.nextLine();
//        AddressDTO[] addressDTOS = gson.fromJson(listJson, AddressDTO[].class);


        // untyped List - comes as List of LinkedTreeMap objects
//        List addressList = gson.fromJson(listJson, List.class);



        // from object to JSON
        CreateEmployeeDTO createEmployeeDTO = new CreateEmployeeDTO("Gosho", BigDecimal.TEN, LocalDate.now(), addressDTO1);

        String json = gson.toJson(createEmployeeDTO);
        System.out.println(json);

        // from JSON to object
        String input = this.scanner.nextLine();

        CreateEmployeeDTO parsedDTO = gson.fromJson(input, CreateEmployeeDTO.class);
        System.out.println(parsedDTO);
    }
}

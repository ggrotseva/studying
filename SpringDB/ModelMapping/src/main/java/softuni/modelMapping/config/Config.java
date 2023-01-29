package softuni.modelMapping.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import softuni.modelMapping.entities.dtos.xml.AddressXmlDTO;
import softuni.modelMapping.entities.dtos.xml.CityXmlDTO;
import softuni.modelMapping.repositories.AddressRepository;
import softuni.modelMapping.services.AddressService;
import softuni.modelMapping.services.AddressServiceImpl;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.util.Scanner;

@Configuration
public class Config {

    @Bean(name = "cityContext")
    public JAXBContext createCityContext() throws JAXBException {
        return JAXBContext.newInstance(CityXmlDTO.class);
    }

    @Bean("addressContext")
    public JAXBContext createAddressContext() throws JAXBException {
        return JAXBContext.newInstance(AddressXmlDTO.class);
    }

//    @Bean
//    public JAXBContext createContext(Class dtoClass) throws JAXBException {
//        return JAXBContext.newInstance(dtoClass);
//    }

    @Bean
    public ModelMapper createModelMapper() {
        return new ModelMapper();
    }

    @Bean
    public Scanner createScanner() {
        return new Scanner(System.in);
    }

//    @Bean
//    public AddressService createAddressService(AddressRepository repo, ModelMapper mapper) {
//        return new AddressServiceImpl(repo, mapper);
//    }

    @Bean
    public Gson createGson() {
        return new GsonBuilder()
//                .setPrettyPrinting()
                .excludeFieldsWithoutExposeAnnotation()
                .create();
    }
}

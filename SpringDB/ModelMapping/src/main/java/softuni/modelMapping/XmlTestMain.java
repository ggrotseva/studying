package softuni.modelMapping;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import softuni.modelMapping.entities.dtos.xml.AddressXmlDTO;
import softuni.modelMapping.entities.dtos.xml.CityXmlDTO;
import softuni.modelMapping.entities.dtos.xml.CountryXmlDTO;

import javax.xml.bind.*;
import java.io.FileWriter;
import java.io.ObjectOutputStream;
import java.util.List;

@Component
public class XmlTestMain implements CommandLineRunner {


    private final JAXBContext addressContext;

    private final JAXBContext cityContext;

    @Autowired
    public XmlTestMain(@Qualifier("cityContext") JAXBContext cityContext,
                       @Qualifier("addressContext") JAXBContext addressContext) {
        this.cityContext = cityContext;
        this.addressContext = addressContext;
    }

    @Override
    public void run(String... args) throws Exception {
        AddressXmlDTO addressDTO = new AddressXmlDTO(4, "Bulgaria", "Kustendil");
        CityXmlDTO cityDTO = new CityXmlDTO("Asenovgrad");

        Marshaller addressMarshaller = addressContext.createMarshaller();
        addressMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        Marshaller cityMarshaller = cityContext.createMarshaller();
        cityMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        cityMarshaller.marshal(cityDTO, System.out);
        addressMarshaller.marshal(addressDTO, System.out);

    }

    private void marshallingTest() throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(CountryXmlDTO.class);

        // to XML
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

//        CitiesXmlDTO cityDTO = new CitiesXmlDTO("Plovdiv", "Pleven", "Ruse");

        CityXmlDTO cityDTO1 = new CityXmlDTO("Plovdiv");
        CityXmlDTO cityDTO2 = new CityXmlDTO("Pleven");
        CityXmlDTO cityDTO3 = new CityXmlDTO("Ruse");
        List<CityXmlDTO> cities = List.of(cityDTO1, cityDTO2, cityDTO3);

        CountryXmlDTO addressDTO = new CountryXmlDTO(5, "Bulgaria", cities);

        marshaller.marshal(addressDTO, System.out);

        // from XML
        Unmarshaller unmarshaller = context.createUnmarshaller();

        CountryXmlDTO unmarshalledCountry = (CountryXmlDTO) unmarshaller.unmarshal(System.in);

        System.out.println(unmarshalledCountry);
    }

}

package softuni.modelMapping;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import softuni.modelMapping.entities.dtos.address.AddressXmlDTO;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

@Component
public class XmlTestMain implements CommandLineRunner {


    @Override
    public void run(String... args) throws Exception {

        AddressXmlDTO xmlDTO = new AddressXmlDTO(5, "Bulgaria", "Pleven");

        JAXBContext context = JAXBContext.newInstance(AddressXmlDTO.class);

        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        marshaller.marshal(xmlDTO, System.out);
    }
}

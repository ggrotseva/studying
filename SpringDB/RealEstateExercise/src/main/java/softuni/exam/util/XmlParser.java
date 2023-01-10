package softuni.exam.util;

import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;

@Component
public class XmlParser {

    public <T> T parseXml(String filePath, Class<T> clas) throws JAXBException {
        File file = new File(filePath);

        JAXBContext context = JAXBContext.newInstance(clas);

        Unmarshaller unmarshaller = context.createUnmarshaller();

        return (T) unmarshaller.unmarshal(file);
    }
}

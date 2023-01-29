package softuni.exam.util;

import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.nio.file.Path;

@Component
public class XmlParser {

    public <T> T fromFile(Class<T> objClass, Path filePath) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(objClass);

        Unmarshaller unmarshaller = context.createUnmarshaller();

        File file = filePath.toFile();

        return (T) unmarshaller.unmarshal(file);
    }
}

package softuni.exam.util;

import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.nio.file.Path;

@Component
public class XmlParserImpl implements XmlParser {

    @Override
    public <T> T fromXml(String filePath, Class<T> typeClass) throws JAXBException {
        final JAXBContext context = JAXBContext.newInstance(typeClass);
        final Unmarshaller unmarshaller = context.createUnmarshaller();

        final File file = Path.of(filePath).toFile();

        return (T) unmarshaller.unmarshal(file);
    }
}

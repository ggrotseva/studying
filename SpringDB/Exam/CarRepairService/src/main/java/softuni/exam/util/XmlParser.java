package softuni.exam.util;

import javax.xml.bind.JAXBException;

public interface XmlParser {

    <T> T fromXml(String filePath, Class<T> typeClass) throws JAXBException;
}

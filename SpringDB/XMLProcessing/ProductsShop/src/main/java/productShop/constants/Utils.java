package productShop.constants;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.nio.file.Path;

public enum Utils {
    ;

    public static <T> void writeXmlIntoFile(T object, Path outputPath) throws JAXBException {

        final JAXBContext context = JAXBContext.newInstance(object.getClass());

        final Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        final File file = outputPath.toFile();

        marshaller.marshal(object, file);
    }
}

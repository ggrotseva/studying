package carDealer.constants;

import com.google.gson.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public enum Utils {
    ;

    private static class LocalDateDeserializer implements JsonDeserializer<LocalDate> {
        @Override
        public LocalDate deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            String dateWithoutBrackets = json.toString().substring(1, json.toString().length() - 1);
            return LocalDate.parse(dateWithoutBrackets, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        }
    }

    private static class LocalDateSerializer implements JsonSerializer<LocalDate> {
        @Override
        public JsonElement serialize(LocalDate src, Type typeOfSrc, JsonSerializationContext context) {
            LocalDateTime dateTime = LocalDateTime.of(src, LocalTime.MIN);
            return new JsonPrimitive(dateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        }
    }

    public static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(LocalDate.class, new LocalDateDeserializer())
            .registerTypeAdapter(LocalDate.class, new LocalDateSerializer())
            .create();


    public static <T> void writeXmlIntoFile(T object, Path outputPath) throws JAXBException {

        final JAXBContext context = JAXBContext.newInstance(object.getClass());

        final Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        final File file = outputPath.toFile();

        marshaller.marshal(object, file);
    }
}

package carDealer.constants;

import com.google.gson.*;

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

    private static final JsonDeserializer<LocalDate> localDateDeserializer = (json, dateType, context) -> {
        String dateWithoutBrackets = json.toString().substring(1, json.toString().length() - 1);
        return LocalDate.parse(dateWithoutBrackets, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    };

    private static final JsonSerializer<LocalDate> localDateSerializer = (source, sourceType, context) -> {
        LocalDateTime dateTime = LocalDateTime.of(source, LocalTime.MIN);
        return new JsonPrimitive(dateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
    };

    public static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(LocalDate.class, localDateDeserializer)
            .registerTypeAdapter(LocalDate.class, localDateSerializer)
            .create();

    public static void writeJsonIntoFile(List<?> objects, Path filePath) throws IOException {
        final FileWriter fileWriter = new FileWriter(filePath.toFile());

        GSON.toJson(objects, fileWriter);

        fileWriter.flush();
        fileWriter.close();
    }
}

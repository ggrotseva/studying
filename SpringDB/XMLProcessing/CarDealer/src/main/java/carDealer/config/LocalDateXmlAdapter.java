package carDealer.config;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class LocalDateXmlAdapter extends XmlAdapter<String, LocalDate> {

    @Override
    public LocalDate unmarshal(String data) throws Exception {
        return LocalDate.parse(data, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    @Override
    public String marshal(LocalDate data) throws Exception {
        return LocalDateTime.of(data, LocalTime.MIN).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }
}

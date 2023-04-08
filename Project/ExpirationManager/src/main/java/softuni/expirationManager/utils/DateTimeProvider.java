package softuni.expirationManager.utils;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class DateTimeProvider {

    public LocalDate getDateToday() {
        return LocalDate.now();
    }

    public LocalDate getDateOneMonthForward() {
        return LocalDate.now().plusMonths(1);
    }

    public LocalDateTime getDateTimeNow() {
        return LocalDateTime.now();
    }
}

package softuni.expirationManager.utils;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class UpdateHomepageScheduler {

    @Scheduled(cron = "0 0 0 * * *")
    public void updateHomepage() {

    }
}

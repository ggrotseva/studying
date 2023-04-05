package softuni.expirationManager.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import softuni.expirationManager.model.dtos.user.UserProfileDTO;
import softuni.expirationManager.service.EmailService;
import softuni.expirationManager.service.UserService;

import java.util.List;

@Component
public class SendDailyEmailScheduler {

    private final EmailService emailService;
    private final UserService userService;

    @Autowired
    public SendDailyEmailScheduler(EmailService emailService, UserService userService) {
        this.emailService = emailService;
        this.userService = userService;
    }

    @Scheduled(cron = "0 3 9 * * *")
    public void sendDailyEmail() {

        List<UserProfileDTO> users = this.userService.getSubscribedUsersInfo();

        for (UserProfileDTO user : users) {
            this.emailService.sendEmail(user.getUsername(), user.getEmail());
        }
    }

//    @Scheduled(cron = "0 * * * * *")
//    public void testSendDailyEmail() {
//
//        List<UserProfileDTO> users = this.userService.getSubscribedUsersInfo();
//
//        for (UserProfileDTO user : users) {
//            this.emailService.sendEmail(user.getUsername(), user.getEmail());
//        }
//    }
}

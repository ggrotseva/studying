package users.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import users.models.User;
import users.repositories.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void printAllUsernameEmailWithProvider(String emailProvider) {
        List<User> users = this.userRepository.findAllByEmailEndingWith(emailProvider);

        if (users.isEmpty()) {
            System.out.println("No users found with email domain " + emailProvider);
        } else {
            users.forEach(u -> System.out.println(u.getUsername() + " " + u.getEmail()));
        }
    }


    @Transactional
    public int updateIsDeletedUserLoggedInBefore(String dateString) {
        LocalDateTime cornerDate = parseDate(dateString);

        int updatedCount = this.userRepository.countByLastTimeLoggedInBefore(cornerDate);

        this.userRepository.updateIsDeletedUserLoggedInBefore(cornerDate);

        return updatedCount;
    }

    private LocalDateTime parseDate(String dateString) {
        // Date string in the format: yyyy.mm.dd / yyyy/mm/dd / yyyy:mm:dd / yyyy-mm-dd /
        //                            dd.mm.yyyy / dd/mm/yyyy / dd:mm:yyyy / dd-mm-yyyy

        String[] tokens = dateString.split("[.:/-]");

        int year = Integer.parseInt(tokens[0]);
        int month = Integer.parseInt(tokens[1]);
        int day = Integer.parseInt(tokens[2]);

        if (tokens[0].length() < 4) {
            day = Integer.parseInt(tokens[0]);
            year = Integer.parseInt(tokens[2]);
        }

        return LocalDateTime.of(year, month, day, 0, 0, 0);
    }

    @Transactional
    public void removeDeletedUsers() {
        this.userRepository.deleteByIsDeletedIsTrue();
    }
}

package users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import users.services.CountryService;
import users.services.TownService;
import users.services.UserService;

import java.util.Scanner;

@Component
public class ConsoleRunner implements CommandLineRunner {

    private final UserService userService;
    private final TownService townService;
    private final CountryService countryService;

    @Autowired
    public ConsoleRunner(UserService userService, TownService townService, CountryService countryService) {
        this.userService = userService;
        this.townService = townService;
        this.countryService = countryService;
    }

    @Override
    public void run(String... args) throws Exception {
        Scanner scan = new Scanner(System.in);

        String input = scan.nextLine();

        int updated = this.userService.updateIsDeletedUserLoggedInBefore(input);
        System.out.println("Users updated/deleted: " + updated);

        this.userService.removeDeletedUsers();

//        this.userService.printAllUsernameEmailWithProvider(input);

    }

}

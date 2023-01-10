package bookshop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ConsoleRunner implements CommandLineRunner {

    private ConsoleRunnerService consoleRunnerService;

    @Autowired
    public ConsoleRunner(ConsoleRunnerService consoleRunnerService) {
        this.consoleRunnerService = consoleRunnerService;
    }

    @Override
    public void run(String... args) {

        // 1
//        this.consoleRunnerService.booksTitlesByAgeRestriction();

        // 2
//        this.consoleRunnerService.booksTitlesByEditionType();

        // 3
//        this.consoleRunnerService.booksByPrice();

        // 4
//        this.consoleRunnerService.notReleasedBooks();

        // 5
//        this.consoleRunnerService.booksReleasedBefore();

        // 6
//        this.consoleRunnerService.authorsSearch();

        // 7
//        this.consoleRunnerService.booksSearch();

        // 8
//        this.consoleRunnerService.booksTitleSearch();

        // 9
//        this.consoleRunnerService.countBooks();

        // 10
//        this.consoleRunnerService.totalBookCopies();

        // 11
//        this.consoleRunnerService.reduceBook();

        // 12
//        this.consoleRunnerService.increaseBookCopies();

        // 13
//        this.consoleRunnerService.removeBooks();

        // 14
//        this.consoleRunnerService.countBooksByAuthor();

    }

}

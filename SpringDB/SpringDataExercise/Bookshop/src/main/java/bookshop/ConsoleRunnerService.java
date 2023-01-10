package bookshop;

import bookshop.domain.dto.BookInformation;
import bookshop.domain.entities.Author;
import bookshop.domain.entities.Book;
import bookshop.services.author.AuthorService;
import bookshop.services.book.BookService;
import bookshop.services.seed.SeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

@Component
public class ConsoleRunnerService {
    // Every method here is public and does all the work (included scanner and parsing)
    // ! Only needs to be called from the ConsoleRunner !

    private final LocalDate YEAR_2K = LocalDate.of(2000, 12, 31);
    private final LocalDate YEAR_1990 = LocalDate.of(1990, 1, 1);
    private final String AUTHOR_NAME = "George Powell";

    private final String BOOK_TITLE_PRICE_FORMAT = "%s - $%.2f";
    private final String BOOK_TITLE_EDITION_PRICE_FORMAT = "%s %s $%.2f";
    private final String BOOK_TITLE_AUTHOR_FORMAT = "%s (%s %s)";

    private final Scanner scan;
    private final BookService bookService;
    private final AuthorService authorService;

    @Autowired
    public ConsoleRunnerService(SeedService seedService, BookService bookService, AuthorService authorService) {
        this.scan = new Scanner(System.in);
        this.bookService = bookService;
        this.authorService = authorService;
    }


    public void getAllBooksAfterYear2000() {
        final List<Book> booksAfter2000 = this.bookService.findAllByReleaseDateAfter(YEAR_2K);

        booksAfter2000.forEach(book -> System.out.println(book.getTitle()));
    }

    public void getAllAuthorsWithBooksBeforeYear1990() {
        final List<Author> authorsWithBooksBefore1990 = this.authorService.findByBooksReleaseDateBefore(YEAR_1990);

        authorsWithBooksBefore1990.stream().filter(author -> author.getBooks().size() > 1)
                .forEach(author -> System.out.println(author.getFirstName() + " " + author.getLastName()));
    }

    public void getAllAuthorsOrderByBooks() {
        final List<Author> authorsWithBooksCount = this.authorService.findAllOrderByBooksSize();

        authorsWithBooksCount
                .forEach(author -> System.out.println(author.getFirstName()
                        + " " + author.getLastName()
                        + " " + author.getBooks().size()));
    }

    public void getAllBooksByAuthor() {
        final String firstName = AUTHOR_NAME.split(" ")[0];
        final String lastName = AUTHOR_NAME.split(" ")[1];

        final List<Book> booksByAuthor = this.bookService.findAllByAuthorFirstNameAndAuthorLastNameOrderByReleaseDateDescTitle(firstName, lastName);

        booksByAuthor
                .forEach(book -> System.out.println(book.getTitle()
                        + " " + book.getReleaseDate()
                        + " " + book.getCopies()));
    }

    // 1
    public void booksTitlesByAgeRestriction() {
        final String ageRestriction = scan.nextLine().toUpperCase();

        this.bookService.findAllByAgeRestriction(ageRestriction)
                .stream().map(Book::getTitle)
                .forEach(System.out::println);
    }

    // 2
    public void booksTitlesByEditionType() {
        final String editionType = scan.nextLine().toUpperCase();
        final int maxCopies = Integer.parseInt(scan.nextLine());

        this.bookService
                .findAllByEditionType(editionType, maxCopies)
                .stream().map(Book::getTitle)
                .forEach(System.out::println);
    }

    // 3
    public void booksByPrice() {
        final String lowPrice = scan.nextLine();
        final String highPrice = scan.nextLine();

        final BigDecimal lowPriceBorder = new BigDecimal(lowPrice);
        final BigDecimal highPriceBorder = new BigDecimal(highPrice);

        this.bookService.findAllByPriceLessThanOrPriceGreaterThan(lowPriceBorder, highPriceBorder)
                .stream().map(b -> String.format(BOOK_TITLE_PRICE_FORMAT, b.getTitle(), b.getPrice()))
                .forEach(System.out::println);
    }

    // 4
    public void notReleasedBooks() {
        final Integer releaseYear = Integer.parseInt(scan.nextLine());

        this.bookService.findAllByReleaseDateNotInYear(releaseYear)
                .stream().map(Book::getTitle)
                .forEach(System.out::println);
    }

    // 5
    public void booksReleasedBefore() {
        final String input = scan.nextLine();

        final LocalDate date = LocalDate.parse(input, DateTimeFormatter.ofPattern("dd-MM-yyyy"));

        this.bookService.findAllByReleaseDateLessThan(date)
                .stream().map(b -> String.format(BOOK_TITLE_EDITION_PRICE_FORMAT, b.getTitle(), b.getEditionType(), b.getPrice()))
                .forEach(System.out::println);
    }

    // 6
    public void authorsSearch() {
        final String nameEnd = scan.nextLine();

        this.authorService.findAllByFirstNameEndingWith(nameEnd)
                .stream().map(a -> a.getFirstName() + " " + a.getLastName())
                .forEach(System.out::println);
    }

    // 7
    public void booksSearch() {
        final String input = scan.nextLine();

        this.bookService.findAllByTitleContaining(input)
                .stream().map(Book::getTitle)
                .forEach(System.out::println);
    }

    // 8
    public void booksTitleSearch() {
        final String input = scan.nextLine();

        this.bookService.findAllByAuthorLastNameStartingWith(input)
                .stream().map(b -> String.format(BOOK_TITLE_AUTHOR_FORMAT, b.getTitle(), b.getAuthor().getFirstName(), b.getAuthor().getLastName()))
                .forEach(System.out::println);
    }

    // 9
    public void countBooks() {
        final int length = Integer.parseInt(scan.nextLine());

        System.out.println(this.bookService.countAllWithTitleLongerThan(length));
    }

    // 10
    public void totalBookCopies() {
        final String[] authorFullName = scan.nextLine().split(" ");
        final String firstName = authorFullName[0];
        final String lastName = authorFullName[1];

        System.out.println(this.authorService.sumOfBookCopiesByAuthor(firstName, lastName));
    }

    // 11
    public void reduceBook() {
        final String title = scan.nextLine();

        final BookInformation bookInformation = this.bookService.findFirstByTitle(title);

        System.out.println(bookInformation);
    }

    // 12
    public void increaseBookCopies() {
//        Locale.setDefault(Locale.ENGLISH);

        final String input = scan.nextLine();
        final DateTimeFormatter format = DateTimeFormatter.ofPattern("dd MMM yyyy")
                // without this, it parses month in BG (Locale.BG) and SQL doesn't recognize it
                .withLocale(Locale.ENGLISH);

        LocalDate date = LocalDate.parse(input, format);
        final int copiesToAdd = Integer.parseInt(scan.nextLine());

        final int updatedBooks = this.bookService.increaseBookCopiesWithReleaseDateAfter(date, copiesToAdd);

        final int totalCopiesAdded = updatedBooks * copiesToAdd;

        System.out.println(totalCopiesAdded);
    }

    // 13
    public void removeBooks() {
        final int copies = Integer.parseInt(scan.nextLine());

        System.out.println(this.bookService.removeByCopiesLessThan(copies));
    }

    // 14
    public void countBooksByAuthor() {
        // the query for creating the procedure is in constants.StoredProcedure.STORED_PROCEDURE_QUERY
        // apparently it should have OUT parameter to work

        final String[] authorFullName = scan.nextLine().split(" ");
        final String firstName = authorFullName[0];
        final String lastName = authorFullName[1];

        System.out.println(this.bookService.countBooksWrittenBy(firstName, lastName));
    }
}

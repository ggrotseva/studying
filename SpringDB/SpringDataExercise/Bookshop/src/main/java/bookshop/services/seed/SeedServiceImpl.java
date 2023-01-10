package bookshop.services.seed;

import bookshop.domain.entities.Author;
import bookshop.domain.entities.Book;
import bookshop.domain.entities.Category;
import bookshop.domain.enums.AgeRestriction;
import bookshop.domain.enums.EditionType;
import bookshop.services.author.AuthorService;
import bookshop.services.author.AuthorServiceImpl;
import bookshop.services.book.BookService;
import bookshop.services.book.BookServiceImpl;
import bookshop.services.category.CategoryService;
import bookshop.services.category.CategoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static bookshop.constants.FilePath.*;

@Component
public class SeedServiceImpl implements SeedService {

    private final AuthorService authorService;
    private final BookService bookService;
    private final CategoryService categoryService;

    @Autowired
    public SeedServiceImpl(AuthorServiceImpl authorService, BookServiceImpl bookService, CategoryServiceImpl categoryService) {
        this.authorService = authorService;
        this.bookService = bookService;
        this.categoryService = categoryService;
    }

    @Override
    public void seedAuthors() throws IOException {
        if (!this.authorService.isDataSeeded()) {
            List<String> authors = Files.readAllLines(Path.of(RESOURCE_URL + AUTHORS_FILE_NAME));

            List<Author> authorsList = authors.stream().filter(a -> !a.isBlank())
                    .map(a -> {
                        String[] tokens = a.split("\\s+");
                        return Author.builder()
                                .withFirstName(tokens[0])
                                .withLastName(tokens[1])
                                .build();
                    }).collect(Collectors.toList());

            authorService.seedAuthors(authorsList);
        }
    }

    @Override
    public void seedBooks() throws IOException {
        if (!this.bookService.isDataSeeded()) {
            List<Book> booksList = new LinkedList<>();
            Files.readAllLines(Path.of(RESOURCE_URL + BOOKS_FILE_NAME))
                    .stream().filter(b -> !b.isBlank()).forEach(row -> {

                        String[] data = row.split("\\s+");

                        EditionType editionType = EditionType.values()[Integer.parseInt(data[0])];
                        LocalDate releaseDate = LocalDate.parse(data[1], DateTimeFormatter.ofPattern("d/M/yyyy"));
                        AgeRestriction ageRestriction = AgeRestriction.values()[Integer.parseInt(data[4])];
                        String title = Arrays.stream(data).skip(5).collect(Collectors.joining(" "));

                        Book book = Book.builder()
                                .withTitle(title)
                                .withAuthor(this.authorService.getRandomAuthor())
                                .withEditionType(editionType)
                                .withReleaseDate(releaseDate)
                                .withCopies(Integer.parseInt(data[2]))
                                .withPrice(new BigDecimal(data[3]))
                                .withAgeRestriction(ageRestriction)
                                .withCategories(this.categoryService.getRandomCategories())
                                .build();

                        booksList.add(book);
                    });

            bookService.seedBooks(booksList);
        }
    }

    @Override
    public void seedCategories() throws IOException {
        if (!this.categoryService.isDataSeeded()) {
            List<String> categories = Files.readAllLines(Path.of(RESOURCE_URL + CATEGORIES_FILE_NAME));

            List<Category> categoryList = categories.stream().filter(a -> !a.isBlank())
                    .map(Category::new).collect(Collectors.toList());

            categoryService.seedCategories(categoryList);
        }
    }
}

package bookshop.services.author;

import bookshop.domain.entities.Author;

import java.time.LocalDate;
import java.util.List;

public interface AuthorService {

    void seedAuthors(List<Author> authors);

    boolean isDataSeeded();

    Author getRandomAuthor();

    List<Author> findByBooksReleaseDateBefore(LocalDate releaseDate);

    List<Author> findAllOrderByBooksSize();

    List<Author> findAllByFirstNameEndingWith(String nameEnd);

    Integer sumOfBookCopiesByAuthor(String firstName, String lastName);

}

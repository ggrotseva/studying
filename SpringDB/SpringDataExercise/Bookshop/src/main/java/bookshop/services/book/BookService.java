package bookshop.services.book;

import bookshop.domain.dto.BookInformation;
import bookshop.domain.entities.Book;
import bookshop.domain.enums.EditionType;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Year;
import java.util.List;
import java.util.Optional;

public interface BookService {

    void seedBooks(List<Book> books);

    boolean isDataSeeded();

    List<Book> findAllByReleaseDateAfter(LocalDate releaseDate);

    List<Book> findAllByAuthorFirstNameAndAuthorLastNameOrderByReleaseDateDescTitle(String firstName, String lastName);

    List<Book> findAllByAgeRestriction(String ageRestriction);

    List<Book> findAllByEditionType(String editionType, int copies);

    List<Book> findAllByPriceLessThanOrPriceGreaterThan(BigDecimal lowPrice, BigDecimal highPrice);

    List<Book> findAllByReleaseDateNotInYear(Integer year);

    List<Book> findAllByReleaseDateLessThan(LocalDate date);

    List<Book> findAllByTitleContaining(String word);

    List<Book> findAllByAuthorLastNameStartingWith(String nameEnd);

    Integer countAllWithTitleLongerThan(int length);

    BookInformation findFirstByTitle(String title);

    int increaseBookCopiesWithReleaseDateAfter(LocalDate date, int copiesToAdd);

    int removeByCopiesLessThan(int copies);

    int countBooksWrittenBy(String firstName, String lastName);
}

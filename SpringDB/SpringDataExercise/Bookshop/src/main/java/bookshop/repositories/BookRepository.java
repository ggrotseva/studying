package bookshop.repositories;

import bookshop.domain.dto.BookInformation;
import bookshop.domain.entities.Book;
import bookshop.domain.enums.AgeRestriction;
import bookshop.domain.enums.EditionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<List<Book>> findAllByReleaseDateAfter(LocalDate releaseDate);

    Optional<List<Book>> findAllByAuthorFirstNameAndAuthorLastNameOrderByReleaseDateDescTitle(String firstName, String lastName);

    Optional<List<Book>> findAllByAgeRestriction(AgeRestriction ageRestriction);

    Optional<List<Book>> findAllByEditionTypeAndCopiesLessThan(EditionType editionType, int copies);

    Optional<List<Book>> findAllByPriceLessThanOrPriceGreaterThan(BigDecimal lowPrice, BigDecimal highPrice);

    Optional<List<Book>> findAllByReleaseDateLessThanOrReleaseDateGreaterThan(LocalDate yearStart, LocalDate yearEnd);

    Optional<List<Book>> findAllByReleaseDateLessThan(LocalDate date);

    Optional<List<Book>> findAllByTitleContaining(String word);

    Optional<List<Book>> findAllByAuthorLastNameStartingWith(String nameEnd);

    @Query("SELECT COUNT(b) FROM Book b WHERE CHAR_LENGTH(b.title) > :length")
    Optional<Integer> countAllWithTitleLongerThan(long length);

    Optional<BookInformation> findFirstByTitle(String title);

    @Query("UPDATE Book b SET b.copies = b.copies + :copiesToAdd WHERE b.releaseDate > :date")
    @Modifying
    @Transactional
    int increaseBookCopiesWithReleaseDateAfter(LocalDate date, int copiesToAdd);

    @Transactional
    int removeByCopiesLessThan(int copies);

    @Procedure(value = "usp_authors_books_count")
    int countBooksWrittenBy(String first, String last);
}

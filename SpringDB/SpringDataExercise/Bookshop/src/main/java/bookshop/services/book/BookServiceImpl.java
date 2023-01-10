package bookshop.services.book;

import bookshop.domain.dto.BookInformation;
import bookshop.domain.entities.Book;
import bookshop.domain.enums.AgeRestriction;
import bookshop.domain.enums.EditionType;
import bookshop.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public void seedBooks(List<Book> books) {
        this.bookRepository.saveAll(books);
    }

    @Override
    public boolean isDataSeeded() {
        return this.bookRepository.count() > 0;
    }

    @Override
    public List<Book> findAllByReleaseDateAfter(LocalDate releaseDate) {
        return this.bookRepository.findAllByReleaseDateAfter(releaseDate)
                .orElseThrow(NoSuchElementException::new);
    }

    @Override
    public List<Book> findAllByAuthorFirstNameAndAuthorLastNameOrderByReleaseDateDescTitle(String firstName, String lastName) {
        return this.bookRepository.findAllByAuthorFirstNameAndAuthorLastNameOrderByReleaseDateDescTitle(firstName, lastName)
                .orElseThrow(NoSuchElementException::new);
    }

    @Override
    public List<Book> findAllByAgeRestriction(String ageRestriction) {
        AgeRestriction parsed = AgeRestriction.valueOf(ageRestriction);

        return this.bookRepository.findAllByAgeRestriction(parsed).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public List<Book> findAllByEditionType(String editionType, int copies) {
        EditionType parsed = EditionType.valueOf(editionType);

        return this.bookRepository.findAllByEditionTypeAndCopiesLessThan(parsed, copies).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public List<Book> findAllByPriceLessThanOrPriceGreaterThan(BigDecimal lowPrice, BigDecimal highPrice) {
        return this.bookRepository.findAllByPriceLessThanOrPriceGreaterThan(lowPrice, highPrice)
                .orElseThrow(NoSuchElementException::new);
    }

    public List<Book> findAllByReleaseDateNotInYear(Integer year) {
        return this.bookRepository
                .findAllByReleaseDateLessThanOrReleaseDateGreaterThan(
                        LocalDate.of(year, 1, 1),
                        LocalDate.of(year, 12, 31))
                .orElseThrow(NoSuchElementException::new);
    }

    @Override
    public List<Book> findAllByReleaseDateLessThan(LocalDate date) {
        return this.bookRepository.findAllByReleaseDateLessThan(date).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public List<Book> findAllByTitleContaining(String word) {
        return this.bookRepository.findAllByTitleContaining(word).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public List<Book> findAllByAuthorLastNameStartingWith(String nameEnd) {
        return this.bookRepository.findAllByAuthorLastNameStartingWith(nameEnd).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public Integer countAllWithTitleLongerThan(int length) {
        return this.bookRepository.countAllWithTitleLongerThan(length).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public BookInformation findFirstByTitle(String title) {
        return this.bookRepository
                .findFirstByTitle(title)
                .orElseThrow(NoSuchElementException::new);
    }

    @Override
    public int increaseBookCopiesWithReleaseDateAfter(LocalDate date, int copiesToAdd) {
        return this.bookRepository.increaseBookCopiesWithReleaseDateAfter(date, copiesToAdd);
    }

    @Override
    public int removeByCopiesLessThan(int copies) {
        return this.bookRepository.removeByCopiesLessThan(copies);
    }

    @Override
    public int countBooksWrittenBy(String firstName, String lastName) {
        return this.bookRepository.countBooksWrittenBy(firstName, lastName);
    }


}

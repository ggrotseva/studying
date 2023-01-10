package bookshop.services.author;

import bookshop.domain.entities.Author;
import bookshop.repositories.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public void seedAuthors(List<Author> authors) {
        this.authorRepository.saveAll(authors);
    }

    @Override
    public boolean isDataSeeded() {
        return this.authorRepository.count() > 0;
    }

    @Override
    public Author getRandomAuthor() {
        Long randomAuthorId = new Random().nextLong(1L, this.authorRepository.count());

        return this.authorRepository.findById(randomAuthorId).get();
    }

    @Override
    public List<Author> findByBooksReleaseDateBefore(LocalDate releaseDate) {
        return this.authorRepository.findDistinctByBooksReleaseDateBefore(releaseDate)
                .orElseThrow(NoSuchElementException::new);
    }

    @Override
    public List<Author> findAllOrderByBooksSize() {
        return this.authorRepository.findAllOrderByBooksSize()
                .orElseThrow(NoSuchElementException::new);
    }

    @Override
    public List<Author> findAllByFirstNameEndingWith(String nameEnd) {
        return this.authorRepository.findAllByFirstNameEndingWith(nameEnd).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public Integer sumOfBookCopiesByAuthor(String firstName, String lastName) {
        return this.authorRepository.sumOfBookCopiesByAuthor(firstName, lastName)
                .orElseThrow(NoSuchElementException::new);
    }

}

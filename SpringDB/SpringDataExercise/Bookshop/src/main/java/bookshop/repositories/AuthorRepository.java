package bookshop.repositories;

import bookshop.domain.entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    Optional<List<Author>> findDistinctByBooksReleaseDateBefore(LocalDate releaseDate);

    @Query("select a from Author a order by size(a.books) desc")
    Optional<List<Author>> findAllOrderByBooksSize();

    Optional<List<Author>> findAllByFirstNameEndingWith(String nameEnd);

    @Query("SELECT SUM(b.copies) FROM Book b "
            + "RIGHT JOIN b.author "
            + "WHERE b.author.firstName = :firstName AND b.author.lastName = :lastName")
    Optional<Integer> sumOfBookCopiesByAuthor(String firstName, String lastName);

}

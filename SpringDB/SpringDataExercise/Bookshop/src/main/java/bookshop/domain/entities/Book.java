package bookshop.domain.entities;

import bookshop.domain.enums.AgeRestriction;
import bookshop.domain.enums.EditionType;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "books")
public class Book extends BaseEntity {

    @Column(length = 50, nullable = false)
    private String title;

    @Column(length = 1000)
    private String description;

    @Column(name = "edition_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private EditionType editionType;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private int copies;

    @Column(name = "release_date")
    private LocalDate releaseDate;

    @Column(name = "age_restriction", nullable = false)
    @Enumerated(EnumType.STRING)
    private AgeRestriction ageRestriction;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;

    @ManyToMany
    @JoinTable(name = "books_categories",
    joinColumns = @JoinColumn(name = "book_id", referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name = "category_id", referencedColumnName = "id"))
    private Set<Category> categories;



    public Book() {
        this.categories = new HashSet<>();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public EditionType getEditionType() {
        return editionType;
    }

    public void setEditionType(EditionType editionType) {
        this.editionType = editionType;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getCopies() {
        return copies;
    }

    public void setCopies(int copies) {
        this.copies = copies;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public AgeRestriction getAgeRestriction() {
        return ageRestriction;
    }

    public void setAgeRestriction(AgeRestriction ageRestriction) {
        this.ageRestriction = ageRestriction;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Set<Category> getCategories() {
        return this.categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }




    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        public Book book;

        private Builder() {
            this.book = new Book();
        }

        public Builder withTitle(String title) {
            this.book.setTitle(title);
            return this;
        }

        public Builder withDescription(String description) {
            this.book.setDescription(description);
            return this;
        }

        public Builder withEditionType(EditionType editionType) {
            this.book.setEditionType(editionType);
            return this;
        }

        public Builder withPrice(BigDecimal price) {
            this.book.setPrice(price);
            return this;
        }

        public Builder withCopies(int copies) {
            this.book.setCopies(copies);
            return this;
        }

        public Builder withReleaseDate(LocalDate releaseDate) {
            this.book.setReleaseDate(releaseDate);
            return this;
        }

        public Builder withAgeRestriction(AgeRestriction ageRestriction) {
            this.book.setAgeRestriction(ageRestriction);
            return this;
        }

        public Builder withAuthor(Author author) {
            this.book.setAuthor(author);
            return this;
        }

        public Builder withCategories(Set<Category> categories) {
            this.book.setCategories(categories);
            return this;
        }

        public Book build() {
            return this.book;
        }
    }

}

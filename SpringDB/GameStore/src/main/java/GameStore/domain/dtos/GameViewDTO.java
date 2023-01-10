package GameStore.domain.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class GameViewDTO {

    private String title;

    private BigDecimal price;

    private String description;

    private LocalDate releaseDate;

    public GameViewDTO() {
    }

    public GameViewDTO(String title, BigDecimal price) {
        this.title = title;
        this.price = price;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String toTitleAndPrice() {
        return this.title + " " + this.price;
    }

    public String getAllInfo() {
        return "Title: " + this.title + System.lineSeparator() +
                "Price: " + this.price + System.lineSeparator() +
                "Description: " + this.description + System.lineSeparator() +
                "Release date: " + this.releaseDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }
}

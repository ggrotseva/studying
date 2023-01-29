package GameStore.domain.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;

import static GameStore.constants.Validations.*;

public class GameDTO {

    private Long id;

    private String title;

    private String trailerId;

    private String imageUrl;

    private float size;

    private BigDecimal price;

    private String description;

    private LocalDate releaseDate;

    public GameDTO() {
    }

    public GameDTO(String title, String trailerId, String imageUrl, float size, BigDecimal price, String description, LocalDate releaseDate) {
        setTitle(title);
        setTrailerId(trailerId);
        setImageUrl(imageUrl);
        setSize(size);
        setPrice(price);
        setDescription(description);
        setReleaseDate(releaseDate);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if (title != null &&
                Character.isUpperCase(title.charAt(0)) &&
                title.length() >= 3 &&
                title.length() <= 100) {
            this.title = title;
        } else {
            throw new IllegalArgumentException(INVALID_GAME_TITLE_MESSAGE);
        }
    }

    public String getTrailerId() {
        return trailerId;
    }

    public void setTrailerId(String trailerId) {
        if (trailerId != null && trailerId.length() == 11) {
            this.trailerId = trailerId;
        } else {
            throw new IllegalArgumentException(INVALID_TRAILER_MESSAGE);
        }
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        if (imageUrl != null && (imageUrl.startsWith("http://") || imageUrl.startsWith("https://"))) {
            this.imageUrl = imageUrl;
        }  else {
            throw new IllegalArgumentException(INVALID_IMAGE_URL_MESSAGE);
        }
    }

    public float getSize() {
        return size;
    }

    public void setSize(float size) {
        if (size > 0.0) {
            this.size = size;
        }  else {
            throw new IllegalArgumentException(INVALID_SIZE_MESSAGE);
        }
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        if (price != null && price.compareTo(BigDecimal.ZERO) > 0) {
            this.price = price;
        } else {
            throw new IllegalArgumentException(INVALID_PRICE_MESSAGE);
        }
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if (description != null && description.length() >= 20) {
            this.description = description;
        }  else {
            throw new IllegalArgumentException(INVALID_DESCRIPTION_MESSAGE);
        }
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

}

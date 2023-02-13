package bg.softuni.mobilelele.model.dto;

import bg.softuni.mobilelele.model.enums.Engine;
import bg.softuni.mobilelele.model.enums.Transmission;
import jakarta.validation.constraints.*;

public class OfferAddDTO {

    @NotNull
    @Min(1)
    private Long modelId;

    @NotBlank
    private String description;

    @NotNull
    private Engine engine;

    @NotBlank
    private String imageUrl;

    @NotNull
    private Transmission transmission;

    @NotNull
    @Positive
    private Integer mileage;

    @NotNull
    @Positive
    private Integer price;

    @NotNull
    @Min(1990)
    private Integer year;


    public Long getModelId() {
        return modelId;
    }

    public OfferAddDTO setModelId(Long modelId) {
        this.modelId = modelId;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public OfferAddDTO setDescription(String description) {
        this.description = description;
        return this;
    }

    public Engine getEngine() {
        return engine;
    }

    public OfferAddDTO setEngine(Engine engine) {
        this.engine = engine;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public OfferAddDTO setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public Transmission getTransmission() {
        return transmission;
    }

    public OfferAddDTO setTransmission(Transmission transmission) {
        this.transmission = transmission;
        return this;
    }

    public Integer getMileage() {
        return mileage;
    }

    public OfferAddDTO setMileage(Integer mileage) {
        this.mileage = mileage;
        return this;
    }

    public Integer getPrice() {
        return price;
    }

    public OfferAddDTO setPrice(Integer price) {
        this.price = price;
        return this;
    }

    public Integer getYear() {
        return year;
    }

    public OfferAddDTO setYear(Integer year) {
        this.year = year;
        return this;
    }
}

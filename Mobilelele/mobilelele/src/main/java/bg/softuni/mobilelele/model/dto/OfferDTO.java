package bg.softuni.mobilelele.model.dto;

import bg.softuni.mobilelele.model.enums.Engine;
import bg.softuni.mobilelele.model.enums.Transmission;

import java.math.BigDecimal;

public class OfferDTO {

    private Long id;

    private String imageUrl;

    private String modelBrandName;
    private String modelName;
    private int year;

    private int mileage;
    private BigDecimal price;
    private Engine engine;
    private Transmission transmission;

    public Long getId() {
        return id;
    }

    public OfferDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public OfferDTO setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public String getModelBrandName() {
        return modelBrandName;
    }

    public OfferDTO setModelBrandName(String modelBrandName) {
        this.modelBrandName = modelBrandName;
        return this;
    }

    public String getModelName() {
        return modelName;
    }

    public OfferDTO setModelName(String modelName) {
        this.modelName = modelName;
        return this;
    }

    public int getYear() {
        return year;
    }

    public OfferDTO setYear(int year) {
        this.year = year;
        return this;
    }

    public int getMileage() {
        return mileage;
    }

    public OfferDTO setMileage(int mileage) {
        this.mileage = mileage;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public OfferDTO setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public Engine getEngine() {
        return engine;
    }

    public OfferDTO setEngine(Engine engine) {
        this.engine = engine;
        return this;
    }

    public Transmission getTransmission() {
        return transmission;
    }

    public OfferDTO setTransmission(Transmission transmission) {
        this.transmission = transmission;
        return this;
    }
}

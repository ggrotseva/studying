package bg.softuni.mobilelele.model.dto;

import bg.softuni.mobilelele.model.enums.Engine;
import bg.softuni.mobilelele.model.enums.Transmission;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OfferDetailsDTO {

    private Long id;

    private String imageUrl;

    private String modelBrandName;
    private String modelName;
    private int year;

    private int mileage;
    private BigDecimal price;
    private Engine engine;
    private Transmission transmission;
    private LocalDateTime created;
    private LocalDateTime modified;
    private String sellerFirstName;
    private String sellerLastName;

    public Long getId() {
        return id;
    }

    public OfferDetailsDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public OfferDetailsDTO setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public String getModelBrandName() {
        return modelBrandName;
    }

    public OfferDetailsDTO setModelBrandName(String modelBrandName) {
        this.modelBrandName = modelBrandName;
        return this;
    }

    public String getModelName() {
        return modelName;
    }

    public OfferDetailsDTO setModelName(String modelName) {
        this.modelName = modelName;
        return this;
    }

    public int getYear() {
        return year;
    }

    public OfferDetailsDTO setYear(int year) {
        this.year = year;
        return this;
    }

    public int getMileage() {
        return mileage;
    }

    public OfferDetailsDTO setMileage(int mileage) {
        this.mileage = mileage;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public OfferDetailsDTO setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public Engine getEngine() {
        return engine;
    }

    public OfferDetailsDTO setEngine(Engine engine) {
        this.engine = engine;
        return this;
    }

    public Transmission getTransmission() {
        return transmission;
    }

    public OfferDetailsDTO setTransmission(Transmission transmission) {
        this.transmission = transmission;
        return this;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public OfferDetailsDTO setCreated(LocalDateTime created) {
        this.created = created;
        return this;
    }

    public LocalDateTime getModified() {
        return modified;
    }

    public OfferDetailsDTO setModified(LocalDateTime modified) {
        this.modified = modified;
        return this;
    }

    public String getSellerFirstName() {
        return sellerFirstName;
    }

    public OfferDetailsDTO setSellerFirstName(String sellerFirstName) {
        this.sellerFirstName = sellerFirstName;
        return this;
    }

    public String getSellerLastName() {
        return sellerLastName;
    }

    public OfferDetailsDTO setSellerLastName(String sellerLastName) {
        this.sellerLastName = sellerLastName;
        return this;
    }
}

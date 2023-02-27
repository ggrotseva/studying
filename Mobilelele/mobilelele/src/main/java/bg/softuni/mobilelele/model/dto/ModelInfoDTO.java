package bg.softuni.mobilelele.model.dto;

import bg.softuni.mobilelele.model.enums.Category;

public class ModelInfoDTO {

    private String name;
    private Category category;
    private String imageUrl;
    private Integer startYear;
    private Integer endYear;

    public String getName() {
        return name;
    }

    public ModelInfoDTO setName(String name) {
        this.name = name;
        return this;
    }

    public Category getCategory() {
        return category;
    }

    public ModelInfoDTO setCategory(Category category) {
        this.category = category;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public ModelInfoDTO setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public Integer getStartYear() {
        return startYear;
    }

    public ModelInfoDTO setStartYear(Integer startYear) {
        this.startYear = startYear;
        return this;
    }

    public Integer getEndYear() {
        return endYear;
    }

    public ModelInfoDTO setEndYear(Integer endYear) {
        this.endYear = endYear;
        return this;
    }
}

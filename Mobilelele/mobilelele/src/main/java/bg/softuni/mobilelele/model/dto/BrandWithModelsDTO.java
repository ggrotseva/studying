package bg.softuni.mobilelele.model.dto;

import java.util.List;

public class BrandWithModelsDTO {

    private String name;

    private List<ModelInfoDTO> models;

    public String getName() {
        return name;
    }

    public BrandWithModelsDTO setName(String name) {
        this.name = name;
        return this;
    }

    public List<ModelInfoDTO> getModels() {
        return models;
    }

    public BrandWithModelsDTO setModels(List<ModelInfoDTO> models) {
        this.models = models;
        return this;
    }
}

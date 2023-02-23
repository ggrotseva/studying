package bg.softuni.mobilelele.model.dto;

import jakarta.validation.constraints.NotNull;

public class OfferUpdateDTO extends OfferAddDTO {

    @NotNull
    private Long id;

    public Long getId() {
        return id;
    }

    public OfferUpdateDTO setId(Long id) {
        this.id = id;
        return this;
    }

}

package carDealer.domain.cars;

public class CarImportDTO {

    private String make;
    private String model;
    private long travelledDistance;

    public CarImportDTO() {
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public long getTravelledDistance() {
        return travelledDistance;
    }
}

package carDealer.domain.suppliers;

public class SupplierPartsDTO {

    private Long id;
    private String name;
    private int partsCount;

    public SupplierPartsDTO() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPartsCount() {
        return partsCount;
    }

    public void setPartsCount(int partsCount) {
        this.partsCount = partsCount;
    }
}

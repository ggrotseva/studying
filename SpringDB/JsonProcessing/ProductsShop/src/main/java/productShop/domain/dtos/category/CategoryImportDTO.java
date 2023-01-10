package productShop.domain.dtos.category;

public class CategoryImportDTO {

    private String name;

    public CategoryImportDTO() {
    }

    public String getName() {
        return name;
    }

//    public void setName(String name) {
//        if (name.length() < 3 || name.length() > 15) {
//          throw new IllegalArgumentException("Category name must be between 3 and 15 characters");
//        }
//        this.name = name;
//    }
}

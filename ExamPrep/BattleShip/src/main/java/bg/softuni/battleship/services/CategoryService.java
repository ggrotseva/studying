package bg.softuni.battleship.services;

import bg.softuni.battleship.models.Category;
import bg.softuni.battleship.models.ShipType;
import bg.softuni.battleship.repositories.CategoryRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @PostConstruct
    private void seedCategories() {
        if (this.categoryRepository.count() == 0) {
            List<Category> categoryEntities = Arrays.stream(ShipType.values())
                    .map(Category::new)
                    .collect(Collectors.toList());

            this.categoryRepository.saveAllAndFlush(categoryEntities);
        }
    }


}

package bg.softuni.pathfinder.service;

import bg.softuni.pathfinder.model.dto.CategoryDTO;
import bg.softuni.pathfinder.model.enums.RouteCategory;
import bg.softuni.pathfinder.repository.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper mapper;

    public CategoryService(CategoryRepository categoryRepository, ModelMapper mapper) {
        this.categoryRepository = categoryRepository;
        this.mapper = mapper;
    }

    public CategoryDTO findByName(RouteCategory name) {
        return this.mapper.map(this.categoryRepository.findByName(name).orElseThrow(), CategoryDTO.class);
    }
}

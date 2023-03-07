package bg.softuni.pathfinder.service;

import bg.softuni.pathfinder.model.dto.CategoryDTO;
import bg.softuni.pathfinder.model.dto.RouteAddDTO;
import bg.softuni.pathfinder.model.dto.RouteBriefDTO;
import bg.softuni.pathfinder.model.dto.RouteDTO;
import bg.softuni.pathfinder.model.entities.Category;
import bg.softuni.pathfinder.model.entities.Route;
import bg.softuni.pathfinder.model.enums.RouteCategory;
import bg.softuni.pathfinder.repository.RouteRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RouteService {

    private final RouteRepository routeRepository;
    private final CategoryService categoryService;
    private final UserService userService;
    private final ModelMapper mapper;

    public RouteService(RouteRepository routeRepository,
                        CategoryService categoryService,
                        UserService userService,
                        ModelMapper mapper) {
        this.routeRepository = routeRepository;
        this.categoryService = categoryService;
        this.userService = userService;
        this.mapper = mapper;
    }

    public RouteBriefDTO getMostCommented() {
        return RouteBriefDTO.makeDTO(this.routeRepository.findFirstByOrderByCommentsDesc()
                .orElseThrow(NoSuchElementException::new));
    }

    public List<RouteBriefDTO> getAllRoutesBriefs() {
        return this.routeRepository.findAll().stream()
                .map(RouteBriefDTO::makeDTO)
                .collect(Collectors.toList());
    }

    public void addRoute(RouteAddDTO routeAddDTO, String username) throws IOException {
        Route route = mapper.map(routeAddDTO, Route.class);

        route.setAuthor(userService.findByUsername(username))
                .setGpxCoordinates(new String(routeAddDTO.getGpxCoordinates().getBytes()))
                .setCategories(routeAddDTO.getCategories().stream()
                        .map(c -> this.mapper.map(this.categoryService.findByName(c), Category.class))
                        .collect(Collectors.toSet()));

        this.routeRepository.saveAndFlush(route);
    }

    public List<RouteBriefDTO> findByCategoriesContains(String categoryName) {
        CategoryDTO categoryDTO = this.categoryService.findByName(RouteCategory.valueOf(categoryName.toUpperCase()));

        List<RouteBriefDTO> routes = this.routeRepository.findByCategoriesContains(this.mapper.map(categoryDTO, Category.class))
                .orElseThrow()
                .stream()
                .map(RouteBriefDTO::makeDTO)
                .toList();

        return routes;
    }

    @Transactional
    public RouteDTO findById(Long id) {
        return this.mapper.map(this.routeRepository.findById(id).orElseThrow(), RouteDTO.class);
    }
}

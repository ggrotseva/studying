package bg.softuni.pathfinder.service;

import bg.softuni.pathfinder.model.dto.RouteAddDTO;
import bg.softuni.pathfinder.model.dto.RouteBriefDTO;
import bg.softuni.pathfinder.repository.RouteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class RouteService {

    private final RouteRepository routeRepository;

    public RouteService(RouteRepository routeRepository) {
        this.routeRepository = routeRepository;
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

    public void addRoute(RouteAddDTO routeAddDTO) {

    }
}

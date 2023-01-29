package bg.softuni.pathfinder.service;

import bg.softuni.pathfinder.model.Route;
import bg.softuni.pathfinder.model.dtos.MostCommentedRouteDTO;
import bg.softuni.pathfinder.repository.RouteRepository;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class RouteService {

    private final RouteRepository routeRepository;

    public RouteService(RouteRepository routeRepository) {
        this.routeRepository = routeRepository;
    }

    public MostCommentedRouteDTO getMostCommented() {
        return MostCommentedRouteDTO.makeDTO(this.routeRepository.findFirstByOrderByCommentsDesc()
                .orElseThrow(NoSuchElementException::new));
    }
}

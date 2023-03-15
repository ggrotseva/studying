package bg.softuni.pathfinder.service;

import bg.softuni.pathfinder.model.dto.CommentAddDTO;
import bg.softuni.pathfinder.model.dto.CommentDTO;
import bg.softuni.pathfinder.model.entities.Comment;
import bg.softuni.pathfinder.repository.CommentRepository;
import bg.softuni.pathfinder.repository.RouteRepository;
import bg.softuni.pathfinder.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final RouteRepository routeRepository;
    private final UserRepository userRepository;
    private final ModelMapper mapper;

    public CommentService(CommentRepository commentRepository,
                          RouteRepository routeRepository,
                          UserRepository userRepository,
                          ModelMapper mapper) {
        this.commentRepository = commentRepository;
        this.routeRepository = routeRepository;
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    public List<CommentDTO> getCommentsByRouteId(Long id) {
        return this.commentRepository.findAllByRouteId(id).orElseThrow()
                .stream().map(c -> this.mapper.map(c, CommentDTO.class))
                .collect(Collectors.toList());
    }

    public CommentDTO createComment(CommentAddDTO commentAddDTO, Long routeId, String username) {

        Comment comment = new Comment()
                .setTextContent(commentAddDTO.getTextContent())
                .setRoute(this.routeRepository.findById(routeId).orElseThrow())
                .setAuthor(this.userRepository.findByUsername(username).orElseThrow())
                .setCreated(LocalDateTime.now())
                .setApproved(true);

        return this.mapper.map(comment, CommentDTO.class);
    }

    public void deleteById(Long id) {
        this.commentRepository.deleteById(id);
    }
}

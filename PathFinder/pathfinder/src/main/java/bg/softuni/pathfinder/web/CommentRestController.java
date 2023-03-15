package bg.softuni.pathfinder.web;

import bg.softuni.pathfinder.model.dto.CommentAddDTO;
import bg.softuni.pathfinder.model.dto.CommentDTO;
import bg.softuni.pathfinder.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.security.Principal;
import java.util.List;

@RestController
public class CommentRestController {

    private final CommentService commentService;

    public CommentRestController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/api/{routeId}/comments")
    public ResponseEntity<List<CommentDTO>> getRouteComments(@PathVariable("routeId") Long routeId) {
        return ResponseEntity.ok(this.commentService.getCommentsByRouteId(routeId));
    }

    @PostMapping(value = "/api/{routeId}/comments", consumes = "application/json", produces = "application/json")
    public ResponseEntity<CommentDTO> postComment(Principal principa,
                                                  @PathVariable("routeId") Long routeId,
                                                  @RequestBody CommentAddDTO commentAddDTO) {

        CommentDTO comment = this.commentService.createComment(commentAddDTO, routeId, principa.getName());

        return ResponseEntity.created(URI.create(String.format("/api/%d/comments/%d", routeId, comment.getId())))
                .body(comment);
    }

    @DeleteMapping("api/{routeId}/comments/{id}")
    public ResponseEntity<CommentDTO> deleteComment(@PathVariable("routeId") Long routeId,
                                                    @PathVariable("id") Long commentId,
                                                     Principal principal) {


        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
}

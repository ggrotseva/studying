package bg.softuni.pathfinder.web;

import bg.softuni.pathfinder.model.dto.CommentAddDTO;
import bg.softuni.pathfinder.model.dto.CommentDTO;
import bg.softuni.pathfinder.service.AuthService;
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
    private final AuthService authService;

    public CommentRestController(CommentService commentService, AuthService authService) {
        this.commentService = commentService;
        this.authService = authService;
    }

    @GetMapping("/api/{routeId}/comments/{commentId}")
    private ResponseEntity<CommentDTO> getComment(@PathVariable("commentId") Long commentId) {
        return ResponseEntity.ok(commentService.getCommentDtoById(commentId));
    }

    @GetMapping("/api/{routeId}/comments")
    public ResponseEntity<List<CommentDTO>> getRouteComments(@PathVariable("routeId") Long routeId) {
        return ResponseEntity.ok(this.commentService.getCommentsByRouteId(routeId));
    }

    @PostMapping(value = "/api/{routeId}/comments", consumes = "application/json", produces = "application/json")
    public ResponseEntity<CommentDTO> postComment(Principal principal,
                                                  @PathVariable("routeId") Long routeId,
                                                  @RequestBody CommentAddDTO commentAddDTO) {

        CommentDTO comment = this.commentService.createComment(commentAddDTO, routeId, principal.getName());

        return ResponseEntity.created(URI.create(String.format("/api/%d/comments/%d", routeId, comment.getId())))
                .body(comment);
    }

    @DeleteMapping("/api/{routeId}/comments/{commentId}")
    public ResponseEntity<CommentDTO> deleteComment(@PathVariable("commentId") Long commentId,
                                                    Principal principal) {

        CommentDTO commentForDelete = this.commentService.getCommentDtoById(commentId);

        if (this.authService.authorizePrincipal(principal.getName()) ||
                commentForDelete.getAuthorUsername().equals(principal.getName())) {

            this.commentService.deleteById(commentId);
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
}

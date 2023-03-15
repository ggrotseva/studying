package bg.softuni.pathfinder.repository;

import bg.softuni.pathfinder.model.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    Optional<List<Comment>> findAllByRouteId(Long id);
}

package users.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import users.models.Picture;

@Repository
public interface PictureRepository extends JpaRepository<Picture, Long> {
}

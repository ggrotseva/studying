package users.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import users.models.Album;

@Repository
public interface AlbumRepository extends JpaRepository<Album, Long> {
}

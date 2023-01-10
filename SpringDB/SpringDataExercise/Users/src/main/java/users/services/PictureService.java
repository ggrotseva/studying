package users.services;

import org.springframework.stereotype.Service;
import users.repositories.PictureRepository;

@Service
public class PictureService {

    private final PictureRepository pictureRepository;

    public PictureService(PictureRepository pictureRepository) {
        this.pictureRepository = pictureRepository;
    }
}

package users.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import users.repositories.TownRepository;

@Service
public class TownService {

    private final TownRepository townRepository;

    @Autowired
    public TownService(TownRepository townRepository) {
        this.townRepository = townRepository;
    }

}

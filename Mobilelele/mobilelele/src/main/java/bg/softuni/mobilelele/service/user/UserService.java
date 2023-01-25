package bg.softuni.mobilelele.service.user;

import bg.softuni.mobilelele.repository.UserRepository;
import bg.softuni.mobilelele.service.DatabaseInitService;
import org.springframework.stereotype.Service;

@Service
public class UserService implements DatabaseInitService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void dbInit() {

    }

    @Override
    public boolean isDbInit() {
        return this.userRepository.count() > 0;
    }
}

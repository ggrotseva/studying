package bg.softuni.mobilelele.service;

import bg.softuni.mobilelele.model.dto.UserRegisterDTO;
import bg.softuni.mobilelele.model.entities.UserEntity;
import bg.softuni.mobilelele.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService implements DatabaseInitService {

    private Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    private static final String USER_ROLE = "USER";

    private final UserRepository userRepository;
//    private final UserRoleService userRoleService;
    private final ModelMapper mapper;

    public UserService(UserRepository userRepository,
//                       UserRoleService userRoleService,
                       ModelMapper mapper) {
        this.userRepository = userRepository;
//        this.userRoleService = userRoleService;
        this.mapper = mapper;
    }

    @Override
    public void dbInit() {

    }

    @Override
    public boolean isDbInit() {
        return this.userRepository.count() > 0;
    }

    public void register(UserRegisterDTO userRegisterDTO) {

        UserEntity newUser = mapper.map(userRegisterDTO, UserEntity.class)
                .setActive(true)
                .setCreated(LocalDateTime.now())
//                .setUserRoles(this.userRepository.count() == 0 ?
//                        this.userRoleService.getRoles() :
//                        List.of(this.userRoleService.findByRole(USER_ROLE)))
                ;

        userRepository.save(newUser);
    }
}

package softuni.expirationManager.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.expirationManager.model.entities.UserEntity;
import softuni.expirationManager.model.entities.UserRoleEntity;
import softuni.expirationManager.model.enums.UserRoleEnum;
import softuni.expirationManager.repository.UserRoleRepository;
import softuni.expirationManager.utils.Constants;
import softuni.expirationManager.model.dtos.user.UserProfileDTO;
import softuni.expirationManager.repository.UserRepository;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final ModelMapper mapper;

    @Autowired
    public UserService(UserRepository userRepository,
                       UserRoleRepository userRoleRepository,
                       ModelMapper mapper) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.mapper = mapper;
    }

    public UserProfileDTO getUserInfoById(Long id) {
        return this.mapper.map(this.userRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException(Constants.NO_USER_FOUND)), UserProfileDTO.class);
    }

    public void switchAdminRole(Long userId) {
        UserEntity user = this.userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException(Constants.NO_USER_FOUND));

        Optional<UserRoleEntity> adminRoleOpt = user.getUserRoles().stream().filter(r -> r.getRole().equals(UserRoleEnum.ADMIN)).findFirst();

        if (adminRoleOpt.isPresent()) {
            user.removeRole(adminRoleOpt.get());
        } else {
            UserRoleEntity adminRole = this.userRoleRepository.findByRole(UserRoleEnum.ADMIN)
                    .orElseThrow(() -> new NoSuchElementException(Constants.NO_ROLE_FOUND));

            user.addRole(adminRole);
        }

        this.userRepository.save(user);
    }

    public boolean isAdmin(Long userId) {
        return this.userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException(Constants.NO_USER_FOUND))
                .getUserRoles()
                .stream()
                .anyMatch(r -> r.getRole().equals(UserRoleEnum.ADMIN));
    }
}

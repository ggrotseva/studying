package com.resellerapp.service;

import com.resellerapp.model.dto.UserLoginDTO;
import com.resellerapp.model.dto.UserRegisterDTO;
import com.resellerapp.model.entity.User;
import com.resellerapp.repository.UserRepository;
import com.resellerapp.session.LoggedUser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final ModelMapper mapper;
    private final LoggedUser userSession;

    public AuthService(UserRepository userRepository,
                       ModelMapper mapper,
                       LoggedUser userSession) {
        this.userRepository = userRepository;
        this.mapper = mapper;
        this.userSession = userSession;
    }

    public boolean register(UserRegisterDTO userRegisterDTO) {
        if (!userRegisterDTO.getPassword().equals(userRegisterDTO.getConfirmPassword())) {
            return false;
        }

        User user = this.mapper.map(userRegisterDTO, User.class);

        this.userRepository.saveAndFlush(user);

        return true;
    }

    public boolean login(UserLoginDTO userLoginDTO) {
        Optional<User> user = this.userRepository.findByUsernameAndPassword(
                userLoginDTO.getUsername(),
                userLoginDTO.getPassword());

        if (user.isEmpty()) {
            return false;
        }

        this.userSession.login(user.get());

        return true;
    }

    public void logout() {
        this.userSession.logout();
    }

    public boolean isLoggedIn() {
        return this.userSession.getId() != null;
    }

    public User getLoggedUser() {
        return this.userRepository.findById(userSession.getId()).orElse(null);
    }

    public Long getLoggedUserId() {
        return this.userSession.getId();
    }
}

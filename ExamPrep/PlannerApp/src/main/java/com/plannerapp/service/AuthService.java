package com.plannerapp.service;

import com.plannerapp.model.dtos.UserLoginDTO;
import com.plannerapp.model.dtos.UserRegisterDTO;
import com.plannerapp.model.entity.User;
import com.plannerapp.repo.UserRepository;
import com.plannerapp.session.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final CurrentUser currentUser;

    @Autowired
    public AuthService(UserRepository userRepository, CurrentUser currentUser) {
        this.userRepository = userRepository;
        this.currentUser = currentUser;
    }

    public boolean register(UserRegisterDTO userRegisterDTO) {

        if (!userRegisterDTO.getPassword().equals(userRegisterDTO.getConfirmPassword())) {
            return false;
        }

        User newUser = new User()
                .setUsername(userRegisterDTO.getUsername())
                .setPassword(userRegisterDTO.getPassword())
                .setEmail(userRegisterDTO.getEmail());

        this.userRepository.saveAndFlush(newUser);

        return true;
    }

    public boolean login(UserLoginDTO userLoginDTO) {
        Optional<User> userOpt = this.userRepository.findByUsernameAndPassword(
                userLoginDTO.getUsername(),
                userLoginDTO.getPassword());

        if (userOpt.isEmpty()) {
            return false;
        }

        this.currentUser.login(userOpt.get());

        return true;
    }

    public void logout() {
        this.currentUser.logout();
    }

    public User getLoggedUser() {
        return this.userRepository.findById(currentUser.getId()).orElse(null);
    }

    public boolean isLogged() {
        return this.currentUser.isLogged();
    }

    public boolean isAnonymous() {
        return !this.currentUser.isLogged();
    }
}

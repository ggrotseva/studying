package com.likebookapp.service;

import com.likebookapp.CurrentUser;
import com.likebookapp.model.dtos.UserLoginDTO;
import com.likebookapp.model.dtos.UserRegisterDTO;
import com.likebookapp.model.entity.User;
import com.likebookapp.repository.UserRepository;
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

    public boolean isLogged() {
        return this.currentUser.isLogged();
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
}

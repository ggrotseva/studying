package com.softuni.mvc.services;

import com.softuni.mvc.models.user.User;
import com.softuni.mvc.models.user.LoginDTO;
import com.softuni.mvc.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // implemented just for UserController demo
    public Optional<User> login(LoginDTO loginDTO) {
        Optional<User> optionalUser =
                this.userRepository.findByUsernameAndPassword(loginDTO.getUsername(), loginDTO.getPassword());

        // check if password is hashed
        // mark user as logged if data is correct

        return optionalUser;
    }
}

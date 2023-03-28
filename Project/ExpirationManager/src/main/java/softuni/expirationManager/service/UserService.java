package softuni.expirationManager.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.expirationManager.utils.Constants;
import softuni.expirationManager.model.dtos.user.UserProfileDTO;
import softuni.expirationManager.repository.UserRepository;

import java.util.NoSuchElementException;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper mapper;

    @Autowired
    public UserService(UserRepository userRepository, ModelMapper mapper) {
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    public UserProfileDTO getUserInfoById(Long id) {
        return this.mapper.map(this.userRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException(Constants.NO_USER_FOUND)), UserProfileDTO.class);
    }
}

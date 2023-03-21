package softuni.expirationManager.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.expirationManager.model.dtos.user.UserProfileDTO;
import softuni.expirationManager.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper mapper;

    public UserService(UserRepository userRepository, ModelMapper mapper) {
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    public UserProfileDTO getUserInfoByName(String username) {
        return this.mapper.map(this.userRepository.findByUsername(username).orElseThrow(), UserProfileDTO.class);
    }

    public UserProfileDTO getUserInfoById(Long id) {
        return this.mapper.map(this.userRepository.findById(id).orElseThrow(), UserProfileDTO.class);
    }
}

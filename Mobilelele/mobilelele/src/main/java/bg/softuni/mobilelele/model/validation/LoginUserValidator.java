package bg.softuni.mobilelele.model.validation;

import bg.softuni.mobilelele.model.dto.UserLoginDTO;
import bg.softuni.mobilelele.model.entities.User;
import bg.softuni.mobilelele.repository.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Optional;

public class LoginUserValidator implements ConstraintValidator<ValidateLoginUser, UserLoginDTO> {

    private final UserRepository userRepository;

    public LoginUserValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean isValid(UserLoginDTO userLoginDTO, ConstraintValidatorContext context) {
        Optional<User> user = this.userRepository.findByUsername(userLoginDTO.getUsername());

        return user.isPresent()
                && user.get().getPassword().equals(userLoginDTO.getPassword());
    }

}

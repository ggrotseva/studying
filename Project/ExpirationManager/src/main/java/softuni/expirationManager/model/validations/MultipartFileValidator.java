package softuni.expirationManager.model.validations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

public class MultipartFileValidator implements ConstraintValidator<FileSizeValidator, MultipartFile> {

    private Long maxBytes;
    private String message;

    @Override
    public void initialize(FileSizeValidator constraintAnnotation) {
        this.maxBytes = Long.parseLong(constraintAnnotation.maxSizeInKilobytes()+"000");
        this.message = constraintAnnotation.message() + String.format("Max file size is %s kB.", constraintAnnotation.maxSizeInKilobytes());
    }

    @Override
    public boolean isValid(MultipartFile multipartFile, ConstraintValidatorContext context) {

        if (multipartFile == null) {
            return true;
        }

        long fileSize = multipartFile.getSize();

        boolean isValid = fileSize <= maxBytes;

        if (!isValid) {
            context.buildConstraintViolationWithTemplate(this.message)
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
        }

        return isValid;
    }
}

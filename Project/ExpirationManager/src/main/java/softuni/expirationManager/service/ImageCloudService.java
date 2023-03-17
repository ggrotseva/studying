package softuni.expirationManager.service;

import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class ImageCloudService {

    private Cloudinary cloudinary;

    public ImageCloudService(@Value("${expirationManager.cloudinary.cloud-name}") String cloudinaryName,
                             @Value("${expirationManager.cloudinary.api-key}") String cloudinaryKey,
                             @Value("${expirationManager.cloudinary.api-secret}") String cloudinarySecret) {
        cloudinary = new Cloudinary(Map.of(
                "cloud_name",cloudinaryName,
                "api_key", cloudinaryKey,
                "api_secret", cloudinarySecret,
                "secure", true
        ));
    }

    public String saveImage(MultipartFile multipartFile) {
        String imageId = UUID.randomUUID().toString();

        Map params = Map.of(
                "public_id", imageId,
                "overwrite", true,
                "resource_type", "image");

        File tmpFile = new File(imageId);


        try {
            Files.write(tmpFile.toPath(), multipartFile.getBytes());
            cloudinary.uploader().upload(tmpFile, params);
            Files.delete(tmpFile.toPath());
        } catch (IOException e) {
            throw new NoSuchElementException(e);
        }

        return "http://res.cloudinary.com/dh0d7odiu/image/upload/v1679044082/"
                + imageId + "." + getFileExtension(multipartFile.getOriginalFilename());
    }

    private String getFileExtension(String filename) {
        return filename.substring(filename.lastIndexOf('.' + 1));
    }
}

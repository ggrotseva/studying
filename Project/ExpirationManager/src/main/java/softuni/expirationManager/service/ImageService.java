package softuni.expirationManager.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;

import static softuni.expirationManager.utils.Constants.DEFAULT_ICON_PATH;

@Service
public class ImageService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImageService.class);

    public byte[] getCategoryDefaultIcon() {
        try {
            FileInputStream fis = new FileInputStream(DEFAULT_ICON_PATH);
            return fis.readAllBytes();
        } catch (IOException e) {
            LOGGER.info("An error occurred while reading from path: " + DEFAULT_ICON_PATH);
            return null;
        }
    }

    public byte[] readBytes(MultipartFile multipartFile) {
        try {
            return multipartFile.getBytes();
        } catch (IOException e) {
            LOGGER.info("An error occurred while reading from html form of file: " + multipartFile.getOriginalFilename());
            return null;
        }
    }
}

package softuni.expirationManager.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import java.io.FileInputStream;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ImageServiceTest {

    private ImageService testImageService;

    @BeforeEach
    void setUp() {
        this.testImageService = new ImageService();
    }

    @Test
    void testReadBytes_ReturnsIcon() throws IOException {
        String testImgPath = "src/main/resources/init/pasta.png";

        FileInputStream fis = new FileInputStream(testImgPath);
        byte[] testImageBytes = fis.readAllBytes();
        MockMultipartFile mockMultipartFile = new MockMultipartFile("image", testImageBytes);

        byte[] actualBytes = testImageService.readBytes(mockMultipartFile);

        assertEquals(testImageBytes, actualBytes);
    }
}

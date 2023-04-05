package softuni.expirationManager.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import softuni.expirationManager.model.dtos.product.ProductHomeViewDTO;

import java.util.List;

@Service
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;
    private final ProductService productService;

    @Autowired
    public EmailService(JavaMailSender javaMailSender, TemplateEngine templateEngine, ProductService productService) {
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
        this.productService = productService;
    }

    public void sendEmail(String username, String userEmail) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, "UTF-8");

        try {
            mimeMessageHelper.setFrom("expiration@manager.bg");
            mimeMessageHelper.setTo(userEmail);
            mimeMessageHelper.setSubject("Daily report from your pantry!");
            mimeMessageHelper.setText(composeEmail(username), true);

        } catch (MessagingException e) {
            e.printStackTrace();
        }

        javaMailSender.send(mimeMessageHelper.getMimeMessage());
    }

    private String composeEmail(String username) {

        List<ProductHomeViewDTO> expiredProducts = this.productService.getExpiredProducts(username);
        List<ProductHomeViewDTO> closeToExpiryProducts = this.productService.getCloseToExpiryProducts(username);

        Context context = new Context();

        context.setVariable("expiredProducts", expiredProducts);
        context.setVariable("closeToExpiryProducts", closeToExpiryProducts);

        return templateEngine.process("email/dailyEmail", context);
    }

}

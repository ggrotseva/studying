package users.annotations.password;

import javax.persistence.Column;
import java.lang.annotation.Annotation;

public class UsernameValidator implements Username {

    private Column username;

    private UsernameValidator(Column username) {
        this.username = username;
    }

    @Override
    public int minLength() {
        return this.username.length();
    }

    @Override
    public int maxLength() {
        return 0;
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        return Username.class;
    }
}

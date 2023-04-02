package softuni.expirationManager.service;

public interface DatabaseInitService {

    boolean isDbEmpty();

    void dbInit();
}

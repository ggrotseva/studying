package softuni.expirationManager.service;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DBInit {

    private final List<DatabaseInitService> databaseInitServices;

    @Autowired
    public DBInit(List<DatabaseInitService> databaseInitServices) {
        this.databaseInitServices = databaseInitServices;
    }

    @PostConstruct
    public void postConstruct() {
        dbInit();
    }

    private void dbInit() {
        databaseInitServices.stream()
                .filter(DatabaseInitService::isDbEmpty)
                .forEach(DatabaseInitService::dbInit);
    }
}

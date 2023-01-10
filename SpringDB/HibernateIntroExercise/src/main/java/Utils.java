import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public enum Utils {
    ;
    static EntityManager createEntityManager() {
        return Persistence.createEntityManagerFactory("soft_uni")
                .createEntityManager();
    }
}

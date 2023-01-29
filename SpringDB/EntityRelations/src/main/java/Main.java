import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("relations");
        EntityManager entityManager = emf.createEntityManager();
        entityManager.getTransaction().begin();

//        Vehicle car = new Car("Opel Astra", "gasoline", 4);
//        Vehicle bike = new Bike();
//        Vehicle plane = new Plane("Airbus", "Petrol", 200);
//
//        entityManager.persist(car);
//        entityManager.persist(bike);
//        entityManager.persist(plane);

//        PlateNumber plateNumber = new PlateNumber("CA0573KK");
//        HasCar car1 = new HasCar(plateNumber);
//        HasCar car2 = new HasCar(plateNumber);
//
//        entityManager.persist(plateNumber);
//        entityManager.persist(car1);
//        entityManager.persist(car2);

//        Article article = new Article("Rita Hayworth and the Shawshank redemption");
//        User author = new User("Stephen King");
//        article.setAuthor(author);
//        author.addArticle(article);
//
//        entityManager.persist(author);
//        entityManager.persist(article);

        entityManager.getTransaction().commit();
        entityManager.close();
    }
}

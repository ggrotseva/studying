//import entities.Bike;
//import entities.Car;
//import entities.Plane;
//import entities.Vehicle;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("relations");
        EntityManager entityManager = emf.createEntityManager();
//        entityManager.getTransaction().begin();

//        Vehicle car = new Car("Opel Astra", "gasoline", 4);
//        Vehicle bike = new Bike();
//        Vehicle plane = new Plane("Airbus", "Petrol", 200);
//
//        entityManager.persist(car);
//        entityManager.persist(bike);
//        entityManager.persist(plane);



//        entityManager.getTransaction().commit();
        entityManager.close();
    }
}

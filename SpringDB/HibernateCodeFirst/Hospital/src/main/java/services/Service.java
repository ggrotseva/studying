package services;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;

import java.util.Scanner;

public abstract class Service {

    EntityManager entityManager;

    public Service() {
        this.entityManager = Persistence.createEntityManagerFactory("soft_uni")
                .createEntityManager();
    }

    abstract void add(Scanner scanner);

    abstract void delete(Scanner scanner);

    //    abstract void update(Scanner scanner);

    abstract void view(Scanner scanner);

    abstract void viewAll();

    void closeEntityManager() {
        entityManager.close();
    }
}

import entities.BankAccount;
import entities.BillingDetail;
import entities.CreditCard;
import entities.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;

import java.time.Month;
import java.time.Year;

public class Main {

    public static void main(String[] args) {
        EntityManager entityManager = Persistence.createEntityManagerFactory("soft_uni")
                .createEntityManager();

        entityManager.getTransaction().begin();

//        User user = new User();
//        user.setEmail("alabala@email.bg");
//        user.setFirstName("Alabala");

//        BillingDetail bankAccount = new BankAccount();
//
//        entityManager.persist(bankAccount);
//
//        CreditCard creditCard = new CreditCard();
//        creditCard.setExpirationMonth(Month.JULY);
//        creditCard.setExpirationYear(Year.parse("2027"));
//
//        entityManager.persist(creditCard);

//        creditCard.setOwner(user);

        entityManager.getTransaction().commit();
        entityManager.close();
    }
}

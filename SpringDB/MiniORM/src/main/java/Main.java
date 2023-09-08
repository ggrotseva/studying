import entities.Student;
import entities.User;
import orm.EntityManager;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) throws SQLException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, InstantiationException {

        EntityManager<User> userManager = new EntityManager<>();
        userManager.handleEntityTable(User.class);
        User user = new User("Pesho", 30, LocalDate.now(), "admin");
        userManager.persist(user);

        user.setUsername("Ivan");
        userManager.persist(user); // does an update to the entity

        userManager.persist(new User("Gosho", 18, LocalDate.now(), "user"));

        EntityManager<Student> studentManager = new EntityManager<>();
        studentManager.handleEntityTable(Student.class);
        Student student = new Student("Ivan");
        studentManager.persist(student);

        User firstUser = userManager.findFirst(User.class);

        System.out.println(firstUser.getId() + " " + firstUser.getUsername());

//        Student firstStudent = studentManager.findFirst(Student.class, "name = 'Pesho'");
//        Student firstStudent = studentManager.findFirst(Student.class);

//        System.out.println(firstStudent.getId() + " " + firstStudent.getName());

        userManager.find(User.class, "age > 18 AND registration_date > '2020-12-31'")
                .forEach(System.out::println);

        userManager.find(User.class)
                .forEach(System.out::println);
    }
}

package hiber;

import hiber.config.AppConfig;
import hiber.model.Car;
import hiber.model.User;
import hiber.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;
import java.util.List;

public class MainApp {
   public static void main(String[] args) throws SQLException {
      AnnotationConfigApplicationContext context = 
            new AnnotationConfigApplicationContext(AppConfig.class);

      UserService userService = context.getBean(UserService.class);

      // Add test users with their cars...
      User user1, user2;
      user1 = new User("User1", "Lastname1", "user1@mail.ru");
      Car car1 = new Car("Model", 3);
      user1.setCar(car1);

      user2 = new User("User2", "Lastname2", "user2@mail.ru");
      Car car2 = new Car("Model", 2);
      user2.setCar(car2);

      userService.add(user1);
      userService.add(user2);

      // Show users with their cars...
      List<User> users = userService.listUsers();
      for (User user : users) {
         System.out.println("Id = "+user.getId());
         System.out.println("First Name = "+user.getFirstName());
         System.out.println("Last Name = "+user.getLastName());
         System.out.println("Email = "+user.getEmail());
         if (user.getCar() != null) {
            System.out.println("Car = " + user.getCar().getModel() + " " + user.getCar().getSeries());
         }
         System.out.println();
      }

      // Get some user by car...
      User user = userService.getCarByModelAndSeries("Model", 3);
      System.out.printf("User with name '%s %s' has car '%s %d'",
              user.getFirstName(),
              user.getLastName(),
              user.getCar().getModel(),
              user.getCar().getSeries());
      System.out.println();

      context.close();
   }
}

import application.App;
import application.Validator;
import database.PostgreSQL;
import models.Passenger;
// https://github.com/khanZhomart/AviTraveller-Endterm.git
import java.util.Scanner;

public class Main {
    static final Scanner scan = new Scanner(System.in);
    static final PostgreSQL postgres = PostgreSQL.getInstance();
    static final App app = new App();

    public static void main(String[] args) {
        launch();
    }

    static void launch() {
        System.out.println("*** AviTraveller ***");

        while (true) {
            System.out.println("\n1. Sign in\n2. Sign up\n0. Exit");
            int answer = scan.nextInt();

            switch (answer) {
                case 0:
                    System.exit(0);

                case 1:
                    signIn();
                    break;

                case 2:
                    signUp();
                    launch();
                    break;
            }
        }
    }

    static void signIn() {
        System.out.print("Email: ");
        String email = scan.next();
        if (!Validator.checkEmail(email)) {
            System.out.println("Invalid email, try again.\n");
            signIn();
        }

        System.out.print("Password: ");
        String password = scan.next();

        Passenger passenger = postgres.checkData(password, email);

        if (passenger == null) {
            System.out.println("Wrong email or password. Try again.\n");
            signIn();
        } else
            System.out.println("Welcome, " + passenger.getName() + "!");

        app.navigate(passenger);
    }

    static void signUp() {
        postgres.registerPassenger();
    }
}

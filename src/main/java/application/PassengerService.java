package application;

import exceptions.WrongInputException;
import models.Card;
import models.Passenger;

import java.util.Locale;
import java.util.Scanner;

public class PassengerService {
    static final Scanner scan = new Scanner(System.in);

    /**
     * Method initializes new user and returns it as an object
     *
     * @return {Passenger} new Passenger account
     * @throws WrongInputException
     */
    public static Passenger initPassenger() throws WrongInputException {
        String name, surname, gender, birthd, email, uin, password, confirm;
        System.out.println("\nPlease, fill the form below. It will take no more than 2 minutes");

        System.out.print("\nName: ");
        name = scan.next();
        System.out.print("Surname: ");
        surname = scan.next();
        if (!Validator.checkStrings(name) || !Validator.checkStrings(surname))
            throw new WrongInputException("Name and surname must contain only letters!");

        System.out.print("Gender (Male/Female): ");
        gender = scan.next().toUpperCase(Locale.ROOT);
        if (!Validator.checkStrings(gender))
            throw new WrongInputException("Gender must contain only letters!");

        System.out.print("Date of Birthd (yyyy-mm-dd): ");
        birthd = scan.next();
        if (!Validator.checkDate(birthd))
            throw new WrongInputException("Invalid date, try again.");

        System.out.println("Almost there!");

        System.out.print("\nEmail: ");
        email = scan.next();
        if (!Validator.checkEmail(email))
            throw new WrongInputException("Invalid email, try again.");

        System.out.print("UIN: ");
        uin = scan.next();
        if (!Validator.checkDigits(uin))
            throw new WrongInputException("UIN must contain only digits!");

        System.out.print("Password (>8 letters, one capital letter, one symbol): ");
        password = scan.next();
        if (!Validator.checkPassword(password))
            throw new WrongInputException("Invalid password, try again!");

        System.out.println("\nConfirm (y/n): ");
        confirm = scan.next().toLowerCase(Locale.ROOT);
        if (confirm.equals("n"))
            initPassenger();

        Passenger passenger = new Passenger.PassengerBuilder(name, surname)
                .setGender(gender)
                .setBirthd(birthd)
                .setEmail(email)
                .setUIN(uin)
                .setPassword(password)
                .build();

        if (!Validator.checkAge(passenger.getAge()))
            throw new WrongInputException("Unfortunately, you are not able to register until you are under 18");

        return passenger;
    }

    /**
     * Method initalizes Passenger's payment method and returns it as an object
     *
     * @return {Card}
     * @throws WrongInputException
     */
    public static Card initCard() throws WrongInputException {
        String card_num, card_type, confirm;
        int exp_month, exp_year, card_cvv;

        System.out.print("Card Number: ");
        card_num = scan.next();
        if (!Validator.checkDigits(card_num))
            throw new WrongInputException("Card number must contain only digits!");

        card_type = defineCardType(card_num);

        System.out.print("Card CVV: ");
        card_cvv = scan.nextInt();

        System.out.print("Expiration month: ");
        exp_month = scan.nextInt();

        System.out.print("Expiration year: ");
        exp_year = scan.nextInt();

        System.out.println("\nConfirm (y/n): ");
        confirm = scan.next().toLowerCase(Locale.ROOT);
        if (confirm.equals("n"))
            initPassenger();

        return new Card.CardBuilder()
                .setCardNum(card_num)
                .setCardType(card_type)
                .setCardCVV(card_cvv)
                .setExpDate(exp_month, exp_year)
                .build();
    }

    private static String defineCardType(String card_num) {
        int first = Integer.parseInt(String.valueOf(card_num.charAt(0)));

        switch (first) {
            case 4:
                return "VISA";

            case 5:
                return "MASTERCARD";

            default:
                return "UNKNOWN";
        }
    }
}

package application;

import database.PostgreSQL;
import models.Card;
import models.Flight;
import models.Passenger;
import models.Ticket;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class App {
    private static final Scanner scan = new Scanner(System.in);
    private static final PostgreSQL postgres = PostgreSQL.getInstance();

    int navbar() {
        System.out.print("\n1. Search tickets" +
                "\n2. My tickets" +
                "\n3. My cards" +
                "\n0. Leave" +
                "\n\nAnswer: ");

        return scan.nextInt();
    }

    public void navigate(Passenger passenger) {
        while (true) {
            switch (navbar()) {
                case 0:
                    return;

                case 1:
                    searchTickets(passenger);
                    break;

                case 2:
                    showMyTickets(passenger);
                    break;

                case 3:
                    showMyCards(passenger);
                    break;
            }
        }
    }

    void searchTickets(Passenger passenger) {
        String date, from, to;
        int flight = -1;
        ArrayList<Flight> flights;

        System.out.println("*type 'all' if you would like to see all available tickets");

        System.out.print("Date (yyyy.mm.dd): ");
        date = scan.next().toLowerCase(Locale.ROOT);

        System.out.print("FROM: ");
        from = scan.next();

        System.out.print("TO: ");
        to = scan.next();

        if (date.equals("all")) {
            flights = postgres.getFlights(from, to);

            if (flights != null)
                System.out.println(flights.toString());
            else {
                System.out.println("There are no such flights for these directions");
                return;
            }
        } else {
            if (!Validator.checkDate(date)) {
                System.out.println("Invalid date, try again.");
                searchTickets(passenger);
            }

            flights = postgres.getFlights(date, from, to);

            if (flights != null)
                System.out.println(flights.toString());
            else {
                System.out.println("There are no such flights for these directions on this date");
                return;
            }
        }

        System.out.println("Type flight's ID, if you would like to purchase a ticket.");
        System.out.println("Otherwise, type -1 to leave.");
        purchaseTicket(passenger.getID());
    }

    void showMyTickets(Passenger passenger) {
        String answer;
        ArrayList<Ticket> tickets = postgres.getTickets(passenger.getID());

        if (tickets == null) {
            System.out.println("*empty*");
            System.out.println("Would you like to buy ticket?(y/n)");
            answer = scan.next().toLowerCase(Locale.ROOT);

            if (answer.equals("y"))
                searchTickets(passenger);
        } else
            System.out.println(tickets.toString());
    }

    void showMyCards(Passenger passenger) {
        String answer;
        ArrayList<Card> cards = postgres.getCards(passenger.getID());

        if (cards == null) {
            System.out.println("*empty*");
            System.out.println("Would you like to add card?(y/n)");
            answer = scan.next().toLowerCase(Locale.ROOT);

            if (answer.equals("y"))
                postgres.registerCard(passenger.getID());
        } else
            System.out.println(cards.toString());
    }

    void purchaseTicket(int passenger_id) {
        int flight_id = scan.nextInt();

        if (flight_id != -1) {
            if (postgres.getCards(passenger_id) == null) {
                System.out.println("You have no payment methods! Add at least one.");
                return;
            }

            System.out.print("Confirm (y/n): ");
            String confirm = scan.next().toLowerCase(Locale.ROOT);

            if (confirm.equals("y"))
                postgres.purchaseTicket(passenger_id, flight_id);
        }


    }
}

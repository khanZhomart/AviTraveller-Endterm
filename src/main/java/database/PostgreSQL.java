package database;

import application.PassengerService;
import exceptions.WrongInputException;
import models.Card;
import models.Flight;
import models.Passenger;
import models.Ticket;

import java.sql.*;
import java.util.ArrayList;

public class PostgreSQL {
    private static final String URL = "jdbc:postgresql://localhost:5432/avitrav";
    private static final String DRIVER = "org.postgresql.Driver";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "100591";

    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet result;
    private String query;

    private static PostgreSQL instance;

    private PostgreSQL() {
        try {
            Class.forName(DRIVER);

            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("\n[Connection Established]\n");
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    public static PostgreSQL getInstance() {
        if (PostgreSQL.instance == null)
            PostgreSQL.instance = new PostgreSQL();

        return PostgreSQL.instance;
    }

    public void registerPassenger() {
        try {
            Passenger passenger = PassengerService.initPassenger();

            query = "INSERT INTO passenger(name, surname, gender, birthd, age, email, password, uin)" +
                    " VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, passenger.getName());
            preparedStatement.setString(2, passenger.getSurname());
            preparedStatement.setString(3, passenger.getGender());
            preparedStatement.setDate(4, passenger.getBirthd());
            preparedStatement.setInt(5, passenger.getAge());
            preparedStatement.setString(6, passenger.getEmail());
            preparedStatement.setString(7, passenger.getPassword());
            preparedStatement.setString(8, passenger.getUin());

            preparedStatement.executeUpdate();
            System.out.println("Done! Now try to login...");
        } catch (SQLException | WrongInputException ex) {
            System.out.println(ex.getMessage());
            registerPassenger();
        }
    }

    public void registerCard(int passenger_id) {
        try {
            Card card = PassengerService.initCard();

            query = "INSERT INTO payment_details(passenger_id, card_num, card_type, card_cvv, exp_month, exp_year) " +
                    "VALUES (?, ?, ?, ?, ?, ?);";

            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, passenger_id);
            preparedStatement.setString(2, card.getCard_num());
            preparedStatement.setString(3, card.getCard_type());
            preparedStatement.setInt(4, card.getCard_cvv());
            preparedStatement.setInt(5, card.getExp_month());
            preparedStatement.setInt(6, card.getExp_year());

            preparedStatement.executeUpdate();
            System.out.println("Done! New card was added");
        } catch (SQLException | WrongInputException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public ArrayList<Flight> getFlights(String date, String from, String to) {
        query = "SELECT flight_details.id, flight_details.departure_date,\n" +
                "\t\tflight.departure_time, flight.arrival_time, flight.duration,\n" +
                "\t\tflight.from_location, a.name AS from_city,\n" +
                "\t\tflight.to_location, b.name AS to_city,\n" +
                "\t\tflight_details.available_seats,\n" +
                "\t\tflight_details.price, company.name\n" +
                "FROM flight\n" +
                "\tJOIN flight_details\n" +
                "\t\tON flight_details.flight_id = flight.id\n" +
                "\tJOIN city a\n" +
                "\t\tON flight.from_location = a.code\n" +
                "\tJOIN city b\n" +
                "\t\tON flight.to_location = b.code\n" +
                "\tJOIN company\n" +
                "\t\tON company.id = flight.company_id\n" +
                "WHERE flight_details.departure_date > ? AND a.name = ? AND b.name = ?" +
                "ORDER BY flight_details.departure_date;";

        ArrayList<Flight> flights = new ArrayList<>();

        try {
            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setDate(1, Date.valueOf(date));
            preparedStatement.setString(2, from);
            preparedStatement.setString(3, to);

            result = preparedStatement.executeQuery();

            if (result.isBeforeFirst()) {
                while (result.next()) {
                    Flight flight = new Flight.FlightBuilder(result.getString("departure_time"), result.getString("arrival_time"), result.getString("departure_date"))
                            .setFlight_id(result.getInt("id"))
                            .setFromLocation(result.getString("from_location"), result.getString("from_city"))
                            .setToLocation(result.getString("to_location"), result.getString("to_city"))
                            .setDuration(result.getString("duration"))
                            .setAvailableSeats(result.getInt("available_seats"))
                            .setPrice(result.getInt("price"))
                            .setCompany(result.getString("name"))
                            .build();

                    flights.add(flight);
                }

                return flights;
            } else
                return null;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public ArrayList<Flight> getFlights(String from, String to) {
        query = "SELECT flight_details.id, flight_details.departure_date,\n" +
                "\t\tflight.departure_time, flight.arrival_time, flight.duration,\n" +
                "\t\tflight.from_location, a.name AS from_city,\n" +
                "\t\tflight.to_location, b.name AS to_city,\n" +
                "\t\tflight_details.available_seats,\n" +
                "\t\tflight_details.price, company.name\n" +
                "FROM flight\n" +
                "\tJOIN flight_details\n" +
                "\t\tON flight_details.flight_id = flight.id\n" +
                "\tJOIN city a\n" +
                "\t\tON flight.from_location = a.code\n" +
                "\tJOIN city b\n" +
                "\t\tON flight.to_location = b.code\n" +
                "\tJOIN company\n" +
                "\t\tON company.id = flight.company_id\n" +
                "WHERE a.name = ? AND b.name = ?" +
                "ORDER BY flight_details.departure_date;";

        ArrayList<Flight> flights = new ArrayList<>();

        try {
            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, from);
            preparedStatement.setString(2, to);

            result = preparedStatement.executeQuery();

            if (result.isBeforeFirst()) {
                while (result.next()) {
                    Flight flight = new Flight.FlightBuilder(result.getString("departure_time"), result.getString("arrival_time"), result.getString("departure_date"))
                            .setFlight_id(result.getInt("id"))
                            .setFromLocation(result.getString("from_location"), result.getString("from_city"))
                            .setToLocation(result.getString("to_location"), result.getString("to_city"))
                            .setDuration(result.getString("duration"))
                            .setAvailableSeats(result.getInt("available_seats"))
                            .setPrice(result.getInt("price"))
                            .setCompany(result.getString("name"))
                            .build();

                    flights.add(flight);
                }

                return flights;
            } else
                return null;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public ArrayList<Card> getCards(int passenger_id) {
        query = "SELECT * FROM payment_details WHERE passenger_id = ?;";

        ArrayList<Card> cards = new ArrayList<>();

        try {
            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, passenger_id);

            result = preparedStatement.executeQuery();

            if (result.isBeforeFirst()) {
                while (result.next()) {
                    Card card = new Card.CardBuilder()
                            .setCardNum(result.getString("card_num"))
                            .setCardType(result.getString("card_type"))
                            .setCardCVV(result.getInt("card_cvv"))
                            .setExpDate(result.getInt("exp_month"), result.getInt("exp_year"))
                            .build();

                    cards.add(card);
                }

                return cards;
            } else
                return null;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public ArrayList<Ticket> getTickets(int passenger_id) {
        query = "SELECT ticket.id, flight_details.departure_date,\n" +
                "\t\tpassenger.name, passenger.surname,\n" +
                "\t\tflight.departure_time, flight.arrival_time, flight.duration,\n" +
                "\t\tflight.from_location, a.name AS from_city,\n" +
                "\t\tflight.to_location, b.name AS to_city,\n" +
                "\t\tflight_details.available_seats,\n" +
                "\t\tflight_details.price, company.name AS company\n" +
                "FROM ticket\n" +
                "\tJOIN flight_details\n" +
                "\t\tON flight_details.id = ticket.flight_id\n" +
                "\tJOIN passenger\n" +
                "\t\tON passenger.id = ticket.passenger_id" +
                "\tJOIN flight\n" +
                "\t\tON flight.id = flight_details.flight_id\n" +
                "\tJOIN city a\n" +
                "\t\tON flight.from_location = a.code\n" +
                "\tJOIN city b\n" +
                "\t\tON flight.to_location = b.code\n" +
                "\tJOIN company\n" +
                "\t\tON company.id = flight.company_id\n" +
                "WHERE ticket.passenger_id = ?" +
                "ORDER BY flight_details.departure_date;";

        ArrayList<Ticket> tickets = new ArrayList<>();

        try {
            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, passenger_id);

            result = preparedStatement.executeQuery();

            if (result.isBeforeFirst()) {
                while (result.next()) {
                    Ticket ticket = new Ticket.TicketBuilder(result.getInt("id"))
                            .setDepartureDate(result.getString("departure_date"))
                            .setDepartureTime(result.getString("departure_time"))
                            .setArrivalTime(result.getString("arrival_time"))
                            .setDuration(result.getString("duration"))
                            .setFrom(result.getString("from_location"), result.getString("from_city"))
                            .setTo(result.getString("to_location"), result.getString("to_city"))
                            .setCompany(result.getString("company"))
                            .setPassenger(result.getString("name"), result.getString("surname"))
                            .build();

                    tickets.add(ticket);
                }

                return tickets;
            } else
                return null;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public void purchaseTicket(int passenger_id, int flight_id) {
        query = "INSERT INTO ticket(passenger_id, flight_id) " +
                "VALUES (?, ?);";

        try {
            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, passenger_id);
            preparedStatement.setInt(2, flight_id);

            preparedStatement.executeUpdate();
            System.out.println("Done! Check your ticket in your cabinet.");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public Passenger checkData(String password, String email) {
        query = "SELECT * FROM passenger WHERE email = ?";

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, email);

            result = preparedStatement.executeQuery();

            if (result.isBeforeFirst()) {
                result.next();
                Passenger passenger = new Passenger.PassengerBuilder(result.getString("name"), result.getString("surname"))
                        .setID(result.getInt("id"))
                        .setGender(result.getString("gender"))
                        .setBirthd(result.getString("birthd"))
                        .setEmail(result.getString("email"))
                        .setUIN(result.getString("uin"))
                        .setPassword(result.getString("password"))
                        .build();

                if (passenger.getPassword().equals(password))
                    return passenger;
                else
                    return null;
            } else
                return null;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
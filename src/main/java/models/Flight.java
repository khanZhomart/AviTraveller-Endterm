package models;

public class Flight {
    private String departure_time, arrival_time, duration, from_location, from_city, to_location, to_city, departure_date, company;
    private int available_seats, flight_id, price;

    private Flight(FlightBuilder builder) {
        this.departure_date = builder.departure_date;
        this.departure_time = builder.departure_time;
        this.arrival_time = builder.arrival_time;
        this.duration = builder.duration;
        this.from_location = builder.from_location;
        this.from_city = builder.from_city;
        this.to_location = builder.to_location;
        this.to_city = builder.to_city;
        this.available_seats = builder.available_seats;
        this.company = builder.company;
        this.flight_id = builder.flight_id;
        this.price = builder.price;
    }

    public String getDeparture_time() {
        return departure_time;
    }

    public String getArrival_time() {
        return arrival_time;
    }

    public String getDuration() {
        return duration;
    }

    public int getPrice() {
        return price;
    }

    public String getFrom_location() {
        return from_location;
    }

    public String getFrom_city() {
        return from_city;
    }

    public String getTo_location() {
        return to_location;
    }

    public String getTo_city() {
        return to_city;
    }

    public String getDeparture_date() {
        return departure_date;
    }

    public int getAvailable_seats() {
        return available_seats;
    }

    public String getCompany() {
        return company;
    }

    public int getFlight_id() {
        return flight_id;
    }

    @Override
    public String toString() {
        return "\nFLIGHT NO. " + getFlight_id() + "\n" + getCompany() + " | " + getDeparture_date() + " | " + getDeparture_time() + " <-> " + getArrival_time() + " >>> " + getDuration() +
                "\n" + getFrom_city() + " -> " + getTo_city() + "\nPrice: " + getPrice() + "\n";
    }

    public static class FlightBuilder {
        private String departure_time, arrival_time, duration, from_location, from_city, to_location, to_city, departure_date, company;
        private int available_seats, flight_id, price;

        public FlightBuilder(String departure_time, String arrival_time, String departure_date) {
            this.departure_time = departure_time;
            this.arrival_time = arrival_time;
            this.departure_date = departure_date;
        }

        public FlightBuilder setDuration(String duration) {
            this.duration = duration;
            return this;
        }

        public FlightBuilder setFromLocation(String location, String city) {
            this.from_location = location;
            this.from_city = city;
            return this;
        }

        public FlightBuilder setToLocation(String location, String city) {
            this.to_location = location;
            this.to_city = city;
            return this;
        }

        public FlightBuilder setPrice(int price) {
            this.price = price;
            return this;
        }

        public FlightBuilder setAvailableSeats(int available_seats) {
            this.available_seats = available_seats;
            return this;
        }

        public FlightBuilder setCompany(String company) {
            this.company = company;
            return this;
        }

        public FlightBuilder setFlight_id(int id) {
            this.flight_id = id;
            return this;
        }

        public Flight build() {
            return new Flight(this);
        }
    }
}
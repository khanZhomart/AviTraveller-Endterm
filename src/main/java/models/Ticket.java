package models;

public class Ticket {
    private String departure_time, arrival_time, duration, from_location, from_city, to_location, to_city, departure_date, company;
    private String passengerName, passengerSurname;
    private int ticket_id;

    private Ticket(TicketBuilder builder) {
        this.departure_date = builder.departure_date;
        this.departure_time = builder.departure_time;
        this.arrival_time = builder.arrival_time;
        this.duration = builder.duration;
        this.from_location = builder.from_location;
        this.from_city = builder.from_city;
        this.to_location = builder.to_location;
        this.to_city = builder.to_city;
        this.company = builder.company;
        this.ticket_id = builder.ticket_id;
        this.passengerName = builder.passengerName;
        this.passengerSurname = builder.passengerSurname;
    }

    public int getTicketID() {
        return ticket_id;
    }

    public String getDepartureTime() {
        return departure_time;
    }

    public String getArrivalTime() {
        return arrival_time;
    }

    public String getDuration() {
        return duration;
    }

    public String getFromLocation() {
        return from_location;
    }

    public String getFromCity() {
        return from_city;
    }

    public String getToLocation() {
        return to_location;
    }

    public String getToCity() {
        return to_city;
    }

    public String getDepartureDate() {
        return departure_date;
    }

    public String getCompany() {
        return company;
    }

    public String getPassengerName() {
        return passengerName;
    }

    public String getPassengerSurname() {
        return passengerSurname;
    }

    @Override
    public String toString() {
        return "\nTICKET NO. " + getTicketID() + "\n" +
                "PASSENGER: " + getPassengerName() + " " + getPassengerSurname() + "\n" +
                "\tFROM: " + getFromLocation() + "/" + getFromCity() + "\n" +
                "\tTO: " + getToLocation() + "/" + getToCity() + "\n" +
                "DATE: " + getDepartureDate() +
                "\tTIME: " + getDepartureTime() + " <> " + getArrivalTime() + " >>> " + getDuration() + "\n";
    }

    public static class TicketBuilder {
        private String departure_time, arrival_time, duration, from_location, from_city, to_location, to_city, departure_date, company;
        private String passengerName, passengerSurname;
        private int ticket_id;

        public TicketBuilder(int ticket_id) {
            this.ticket_id = ticket_id;
        }

        public TicketBuilder setDepartureDate(String date) {
            this.departure_date = date;
            return this;
        }

        public TicketBuilder setDepartureTime(String time) {
            this.departure_time = time;
            return this;
        }

        public TicketBuilder setArrivalTime(String time) {
            this.arrival_time = time;
            return this;
        }

        public TicketBuilder setDuration(String duration) {
            this.duration = duration;
            return this;
        }

        public TicketBuilder setFrom(String location, String city) {
            this.from_location = location;
            this.from_city = city;
            return this;
        }

        public TicketBuilder setTo(String location, String city) {
            this.to_location = location;
            this.to_city = city;
            return this;
        }

        public TicketBuilder setCompany(String company) {
            this.company = company;
            return this;
        }

        public TicketBuilder setPassenger(String name, String surname) {
            this.passengerName = name;
            this.passengerSurname = surname;
            return this;
        }

        public Ticket build() {
            return new Ticket(this);
        }
    }
}

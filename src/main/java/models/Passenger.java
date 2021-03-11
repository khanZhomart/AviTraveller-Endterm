package models;

import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;

public class Passenger {
    private String name, surname, gender, email, uin, password;
    private LocalDate birthd;
    private int age, id;

    Passenger(PassengerBuilder builder) {
        this.name = builder.name;
        this.surname = builder.surname;
        this.gender = builder.gender;
        this.email = builder.email;
        this.birthd = builder.birthd;
        this.uin = builder.uin;
        this.password = builder.password;
        this.age = Period.between(birthd, LocalDate.now()).getYears();
        this.id = builder.id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getGender() {
        return gender;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public int getAge() {
        return age;
    }

    public Date getBirthd() {
        return Date.valueOf(birthd);
    }

    public String getUin() {
        return uin;
    }

    public int getID() {
        return id;
    }

    @Override
    public String toString() {
        return "Passenger\n{Name: " + getName() +
                "\nSurname: " + getSurname() +
                "\nGender: " + getGender() +
                "\nEmail: " + getEmail() +
                "\nPassword" + getPassword() +
                "\nDate of birth: " + getBirthd() +
                "\nAge: " + getAge() +
                "\nUIN: " + getUin() +
                "\nID: " + getID() +
                "}";
    }

    public static class PassengerBuilder {
        private String name, surname, gender, email, password, uin;
        private LocalDate birthd;
        private int id;

        public PassengerBuilder(String name, String surname) {
            this.name = name;
            this.surname = surname;
        }

        public PassengerBuilder setGender(String gender) {
            this.gender = gender;
            return this;
        }

        public PassengerBuilder setBirthd(String birthd) {
            this.birthd = LocalDate.parse(birthd);
            return this;
        }

        public PassengerBuilder setEmail(String email) {
            this.email = email;
            return this;
        }

        public PassengerBuilder setPassword(String password) {
            this.password = password;
            return this;
        }

        public PassengerBuilder setUIN(String uin) {
            this.uin = uin;
            return this;
        }

        public PassengerBuilder setID(int id) {
            this.id = id;
            return this;
        }

        public Passenger build() {
            return new Passenger(this);
        }
    }
}

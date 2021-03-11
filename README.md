# AviTraveller — Airline Reservation System
## Configuring Database
  This CLI application uses **PostgreSQL** as a database. Just copy & paste this code into your pgAdmin's query tool:
  ```postgresql
  CREATE TABLE passenger (
    id SERIAL,
    name VARCHAR(32),
    surname VARCHAR(32),
    gender VARCHAR(6),
    birthd DATE,
    age INT,
    email VARCHAR(32),
    password VARCHAR(32),
    UIN INT NOT NULL,
    
    PRIMARY KEY (id),
    UNIQUE (email)
  );
  
  CREATE TABLE payment_details (
    passenger_id INT,
    card_num VARCHAR(32),
    card_type VARCHAR(32),
    card_cvv INT,
    exp_month INT,
    exp_year INT,
    
    FOREIGN KEY (passenger_id) REFERENCES passenger(id)
  );
  
  CREATE TABLE city (
    code CHAR(3),
    name VARCHAR(32),
    
    PRIMARY KEY (code)
  );
  
  CREATE TABLE company (
     id SERIAL,
     name VARCHAR(32),
     
     PRIMARY KEY (id)
  );
  
  CREATE TABLE flight (
    id SERIAL,
    departure_time VARCHAR(32),
    arrival_time VARCHAR(32),
    duration VARCHAR(32),
    from_location CHAR(3),
    to_location CHAR(3),
    total_seats INT,
    company_id INT,
    
    PRIMARY KEY (id),
    FOREIGN KEY (from_location) REFERENCES city(code),
    FOREIGN KEY (to_location) REFERENCES city(code),
    FOREIGN KEY (company_id) REFERENCES company(id)
  );
  
  CREATE TABLE flight_details (
    id SERIAL,
    departure_date VARCHAR(32),
    price INT CONSTRAINT positive_price CHECK (price > 0),
    available_seats INT,
    flight_id INT,
    
    PRIMARY KEY (id),
    FOREIGN KEY (flight_id) REFERENCES flight(id)
  );
  
  CREATE TABLE ticket (
    id SERIAL,
    passenger_id INT,
    flight_id INT,
    
    PRIMARY KEY (id),
    FOREIGN KEY (passenger_id) REFERENCES passenger(id),
    FOREIGN KEY (flight_id) REFERENCES flight_details(id)
  );
```
  Here is some mock data to test out the program:
  ```postgresql
  INSERT INTO city(code, name)
      VALUES ('NQZ', 'Nur-Sultan'), 
              ('CIT', 'Shymkent'), 
              ('DMB', 'Taraz'), 
              ('ALA', 'Almaty'),
              ('AKX', 'Aktobe'),
              ('PWQ', 'Pavlodar'),
              ('PLX', 'Semei'),
              
  INSERT INTO flight(departure_time, arrival_time, duration, from_location, to_location, total_seat, company_id)
	    VALUES ('15:15', '16:15', '1:00', 'ALA', 'NQZ', 160, 1), ('9:30', '10:30', '1:00', 'CIT', 'ALA', 100, 3), ('18:00', '19:20', '1:20', 'ALA', 'PWQ', 130, 2);
	
  INSERT INTO  flight_details(departure_date, price, available_seats, flight_id)
	    VALUES ('2021-1-22', 46558, 109, 1), ('2021-1-22', 42500, 103, 2), ('2021-1-22', 47899, 65, 3);
```
## Java
  All you need to do is to change value of _final variables_ named **USERNAME**, **PASSWORD**, **URL** in class **PostgreSQL.java** to valid one.  

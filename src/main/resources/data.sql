/*INSERT INTO account_types (type)
VALUES ('User'),
       ('Airport Owner'),
       ('Airline Owner'),
       ('Admin');

INSERT INTO users (login, password, email, account_type_id)
VALUES ('loginUser1', 'passwordUser1', 'emailUser1@user1.com', 1),
       ('loginUser2', 'passwordUser2', 'emailUser2@user2.com', 1),
       ('loginAirportOwner1', 'passwordAirportOwner1', 'emailAirportOwner1@AirportOwner1.com', 2),
       ('loginAirportOwner2', 'passwordAirportOwner2', 'emailAirportOwner2@AirportOwner2.com', 2),
       ('loginAirportOwner3', 'passwordAirportOwner3', 'emailAirportOwner3@AirportOwner3.com', 2),
       ('loginAirlineOwner1', 'passwordAirlineOwner1', 'emailAirlineOwner1@AirlineOwner1.com', 3),
       ('loginAirlineOwner2', 'passwordAirlineOwner2', 'emailAirlineOwner2@AirlineOwner2.com', 3),
       ('loginAirlineOwner3', 'passwordAirlineOwner3', 'emailAirlineOwner3@AirlineOwner3.com', 3),
       ('loginAdmin', 'passwordAdmin', 'admin@admin.com', 4);

INSERT INTO airlines (name, user_id)
VALUES ('Qatar Airways', 6),
       ('Skytaxi', 7),
       ('LOT', 8);

INSERT INTO airports (name, capacity, latitude, longitude, active, user_id)
VALUES ('Warsaw Chopin Airport', 18, 52.165833, 20.967222, true, 3),
       ('John Paul II International Airport Kraków–Balice', 7, 50.077778, 19.784722, true, 4),
       ('Rzeszów–Jasionka Airport', 1, 50.110000, 22.018889, true, 5);

INSERT INTO airplanes (name, capacity, maxDistance)
VALUES ('Boeing 737 NG/737 MAX', '188', '2000'),
       ('Airbus A350', '325', '8000'),
       ('Fokker 50', '58', '1000');

INSERT INTO flights (cost, start_date, end_date, start_airport_id, end_airport_id, airplane_id)
VALUES (50, {ts '2020-05-09 18:40:00.00'}, {ts '2020-05-09 21:15:00.00'}, 1, 2, 1),
       (120, {ts '2020-05-09 23:40:00.00'}, {ts '2020-05-10 00:15:00.00'}, 2, 3, 3);


INSERT INTO PUBLIC.tickets (user_id, flight_id)
VALUES (1, 1),
       (2, 2);
*/

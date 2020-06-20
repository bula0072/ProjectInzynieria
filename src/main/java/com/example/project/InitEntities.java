package com.example.project;

import com.example.project.controllers.api.admin.AdminApi;
import com.example.project.controllers.api.airline.AirlineApi;
import com.example.project.controllers.api.airline.FlightApi;
import com.example.project.controllers.api.airplane.AirplaneApi;
import com.example.project.controllers.api.airport.AirportApi;
import com.example.project.controllers.api.user.UserApi;
import com.example.project.controllers.api.user.service.UserCreator;
import com.example.project.dto.AirlineNewDto;
import com.example.project.dto.AirplaneNewDto;
import com.example.project.dto.AirportNewDto;
import com.example.project.dto.UserChangeDto;
import com.example.project.entity.Flight;
import com.example.project.entity.Ticket;
import com.example.project.repository.FlightRepository;
import com.example.project.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class InitEntities implements CommandLineRunner {

    @Autowired
    FlightRepository flightRepository;
    @Autowired
    TicketRepository ticketRepository;
    UserApi userApi;
    AdminApi adminApi;
    AirlineApi airlineApi;
    AirportApi airportApi;
    AirplaneApi airplaneApi;
    FlightApi flightApi;
    UserCreator userCreator;

    public InitEntities(UserApi userApi,
                        AdminApi adminApi,
                        AirlineApi airlineApi,
                        AirportApi airportApi,
                        AirplaneApi airplaneApi,
                        FlightApi flightApi,
                        UserCreator userCreator) {
        this.userApi = userApi;
        this.adminApi = adminApi;
        this.airlineApi = airlineApi;
        this.airportApi = airportApi;
        this.airplaneApi = airplaneApi;
        this.flightApi = flightApi;
        this.userCreator = userCreator;
    }

    @Override
    public void run(String... args) throws Exception {
       /* initUser();
        initAirline();
        initAirport();
        initAirplanes();
        initFlight();
        initTicket();*/
    }

    private void initTicket() {
        ticketRepository.save(new Ticket(
                15,
                userApi.getUserByName("user"),
                flightRepository.findAll().get(0)
        ));
    }

    private void initFlight() {
        flightRepository.save(new Flight(
                50.50,
                Instant.now(),
                Instant.now().plusMillis(500),
                airportApi.getAirportByName("AirportName"),
                airportApi.getAirportByName("AirportName"),
                airplaneApi.getAllAirplanes().get(1)
        ));

        flightRepository.save(new Flight(
                50.50,
                Instant.now().plusMillis(50000),
                Instant.now().plusMillis(55000),
                airportApi.getAirportByName("AirportName"),
                airportApi.getAirportByName("AirportName"),
                airplaneApi.getAllAirplanes().get(0)
        ));

        flightRepository.save(new Flight(
                50.50,
                Instant.now().plusMillis(80000),
                Instant.now().plusMillis(90000),
                airportApi.getAirportByName("AirportName"),
                airportApi.getAirportByName("AirportName"),
                airplaneApi.getAllAirplanes().get(1)
        ));

    }

    private void initAirplanes() {
        airplaneApi.postNewAirplane(new AirplaneNewDto(
                "Airplane",
                100,
                50.10,
                "airlineowner1"
        ));
        airplaneApi.postNewAirplane(new AirplaneNewDto(
                "Airplane123",
                100,
                50.10,
                "airlineowner2"
        ));
    }

    private void initAirport() {
        airportApi.postNewAirport(new AirportNewDto(
                "AirportName",
                10,
                50.50,
                30.30,
                "portowner"
        ));
    }

    private void initAirline() {
        airlineApi.postNewAirline(new AirlineNewDto("AirlineName", "airlineowner1"));
        airlineApi.postNewAirline(new AirlineNewDto("AirlineName123", "airlineowner2"));
    }

    private void initUser() {
        adminApi.changeRole((
                userCreator.create(
                        new UserChangeDto(
                                "userinit1",
                                "password",
                                "emailinit1@gmail.com"
                        ))), "USER");
        adminApi.changeRole((
                userCreator.create(
                        new UserChangeDto(
                                "airlineowner1init1",
                                "password",
                                "airlineowner1init1@gmail.com"
                        ))), "AIRLINE_OWNER");
        adminApi.changeRole((
                userCreator.create(
                        new UserChangeDto(
                                "airlineowner21init1",
                                "password",
                                "airlineowner2init1@gmail.com"
                        ))), "AIRLINE_OWNER");
        adminApi.changeRole((
                userCreator.create(
                        new UserChangeDto(
                                "portownerinit",
                                "password",
                                "airport@init.com"
                        ))), "AIRPORT_OWNER");
        adminApi.changeRole((
                userCreator.create(
                        new UserChangeDto(
                                "admin",
                                "password",
                                "admin@init.com"
                        ))), "ADMIN");
    }
}

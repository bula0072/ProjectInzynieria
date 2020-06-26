package com.example.project;

import com.example.project.controllers.api.admin.AdminApi;
import com.example.project.controllers.api.airline.AirlineApi;
import com.example.project.controllers.api.airplane.AirplaneApi;
import com.example.project.controllers.api.airport.AirportApi;
import com.example.project.controllers.api.flight.FlightApi;
import com.example.project.controllers.api.flight.service.FlightCreator;
import com.example.project.controllers.api.user.UserApi;
import com.example.project.controllers.api.user.service.UserCreator;
import com.example.project.dto.*;
import com.example.project.repository.FlightRepository;
import com.example.project.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class InitEntities implements CommandLineRunner {

	@Autowired
	TicketRepository ticketRepository;
	@Autowired
	FlightRepository flightRepository;
	UserApi userApi;
	AdminApi adminApi;
	AirlineApi airlineApi;
	AirportApi airportApi;
	AirplaneApi airplaneApi;
	FlightApi flightApi;
	UserCreator userCreator;
	FlightCreator flightCreator;


	public InitEntities(UserApi userApi,
						AdminApi adminApi,
						AirlineApi airlineApi,
						AirportApi airportApi,
						AirplaneApi airplaneApi,
						FlightApi flightApi,
						UserCreator userCreator,
						FlightCreator flightCreator) {
		this.userApi = userApi;
		this.adminApi = adminApi;
		this.airlineApi = airlineApi;
		this.airportApi = airportApi;
		this.airplaneApi = airplaneApi;
		this.flightApi = flightApi;
		this.userCreator = userCreator;
		this.flightCreator = flightCreator;
	}

	@Override
	public void run(String... args) throws Exception {
		initUser();

		initAirline();
		initAirport();
		initAirplanes();
		initFlight();
//        initTicket();
	}

	private void initFlight() {
		flightCreator.create(new FlightNewDto(
				50.50,
				Instant.now().toString(),
				Instant.now().plusSeconds(500).toString(),
				"AirportName1",
				"AirportName2",
				airplaneApi.getAllAirplanes().get(1).id
		));

		flightCreator.create(new FlightNewDto(
				50.50,
				Instant.now().plusSeconds(50000).toString(),
				Instant.now().plusSeconds(55000).toString(),
				"AirportName1",
				"AirportName2",
				airplaneApi.getAllAirplanes().get(1).id
		));

		flightCreator.create(new FlightNewDto(
				50.50,
				Instant.now().plusSeconds(80000).toString(),
				Instant.now().plusSeconds(95000).toString(),
				"AirportName1",
				"AirportName2",
				airplaneApi.getAllAirplanes().get(1).id
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
				"airlineowner1"
		));
	}

	private void initAirport() {
		airportApi.postNewAirport(new AirportNewDto(
				"AirportName1",
				10,
				50.50,
				30.30,
				"portowner"
		));
        airportApi.postNewAirport(new AirportNewDto(
                "AirportName2",
                10,
                50.50,
                30.30,
                "portowner2"
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
								"airlineowner1",
								"password",
								"airlineowner1init1@gmail.com"
						))), "AIRLINE_OWNER");
		adminApi.changeRole((
				userCreator.create(
						new UserChangeDto(
								"airlineowner2",
								"password",
								"airlineowner2init1@gmail.com"
						))), "AIRLINE_OWNER");
		adminApi.changeRole((
				userCreator.create(
						new UserChangeDto(
								"portowner",
								"password",
								"airport@init.com"
						))), "AIRPORT_OWNER");
        adminApi.changeRole((
                userCreator.create(
                        new UserChangeDto(
                                "portowner2",
                                "password",
                                "airport2@init.com"
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

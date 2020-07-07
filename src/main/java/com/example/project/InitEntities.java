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
import com.example.project.entity.Airplane;
import com.example.project.entity.Flight;
import com.example.project.repository.FlightRepository;
import com.example.project.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.Random;

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
	public void run(String... args) {
		initUser();

		initAirline();
		initAirport();
		initAirplanes();
		initFlight();
	}

	private void initFlight() {
		List<Airplane> airplanes = airplaneApi.getAllAirplanes();
		List<AirportBasicDto> airports = airportApi.getAllAirportsDto();
		flightRepository.save(new Flight(
				50.50,
				Instant.now().minusSeconds(150000),
				Instant.now().minusSeconds(15000),
				airportApi.getAirportByName("Lotnisko Chopina w Warszawie"),
				airportApi.getAirportByName("Port lotniczy Kraków-Balice im. Jana Pawła II"),
				airplanes.get(new Random().nextInt(airplanes.size()))
		));
		flightCreator.create(new FlightNewDto(
				50.50,
				Instant.now().minusSeconds(500).toString(),
				Instant.now().plusSeconds(500).toString(),
				airports.get(new Random().nextInt(airports.size())).getName(),
				airports.get(new Random().nextInt(airports.size())).getName(),
				airplanes.get(new Random().nextInt(airplanes.size())).id
		));

		flightCreator.create(new FlightNewDto(
				50.50,
				Instant.now().plusSeconds(1800).toString(),
				Instant.now().plusSeconds(3600).toString(),
				airports.get(new Random().nextInt(airports.size())).getName(),
				airports.get(new Random().nextInt(airports.size())).getName(),
				airplanes.get(new Random().nextInt(airplanes.size())).id
		));

		flightCreator.create(new FlightNewDto(
				50.50,
				Instant.now().plusSeconds(5400).toString(),
				Instant.now().plusSeconds(7200).toString(),
				airports.get(new Random().nextInt(airports.size())).getName(),
				airports.get(new Random().nextInt(airports.size())).getName(),
				airplanes.get(new Random().nextInt(airplanes.size())).id
		));

		flightCreator.create(new FlightNewDto(
				50.50,
				Instant.now().plusSeconds(9112).toString(),
				Instant.now().plusSeconds(10765).toString(),
				airports.get(new Random().nextInt(airports.size())).getName(),
				airports.get(new Random().nextInt(airports.size())).getName(),
				airplanes.get(new Random().nextInt(airplanes.size())).id
		));

		flightCreator.create(new FlightNewDto(
				50.50,
				Instant.now().plusSeconds(12678).toString(),
				Instant.now().plusSeconds(14456).toString(),
				airports.get(new Random().nextInt(airports.size())).getName(),
				airports.get(new Random().nextInt(airports.size())).getName(),
				airplanes.get(new Random().nextInt(airplanes.size())).id
		));

		flightCreator.create(new FlightNewDto(
				50.50,
				Instant.now().plusSeconds(16000).toString(),
				Instant.now().plusSeconds(18000).toString(),
				airports.get(new Random().nextInt(airports.size())).getName(),
				airports.get(new Random().nextInt(airports.size())).getName(),
				airplanes.get(new Random().nextInt(airplanes.size())).id
		));

		flightCreator.create(new FlightNewDto(
				50.50,
				Instant.now().plusSeconds(19800).toString(),
				Instant.now().plusSeconds(22000).toString(),
				airports.get(new Random().nextInt(airports.size())).getName(),
				airports.get(new Random().nextInt(airports.size())).getName(),
				airplanes.get(new Random().nextInt(airplanes.size())).id
		));

		flightCreator.create(new FlightNewDto(
				50.50,
				Instant.now().plusSeconds(35000).toString(),
				Instant.now().plusSeconds(38000).toString(),
				airports.get(new Random().nextInt(airports.size())).getName(),
				airports.get(new Random().nextInt(airports.size())).getName(),
				airplanes.get(new Random().nextInt(airplanes.size())).id
		));
	}

	private void initAirplanes() {
		airplaneApi.postNewAirplane(new AirplaneNewDto(
				"Boeing 747",
				366,
				9800.0,
				"al1"
		));
		airplaneApi.postNewAirplane(new AirplaneNewDto(
				"Tu-154",
				114,
				6600.0,
				"al1"
		));
		airplaneApi.postNewAirplane(new AirplaneNewDto(
				"Saab 2000",
				50,
				2185.0,
				"al1"
		));

		airplaneApi.postNewAirplane(new AirplaneNewDto(
				"Fokker 100",
				97,
				3170.0,
				"al1"
		));
		airplaneApi.postNewAirplane(new AirplaneNewDto(
				"An-140",
				52,
				3050.0,
				"al2"
		));
		airplaneApi.postNewAirplane(new AirplaneNewDto(
				"Airbus A320",
				180,
				5700.0,
				"al2"
		));
		airplaneApi.postNewAirplane(new AirplaneNewDto(
				"McDonnell Douglas MD-11",
				181,
				7310.0,
				"al2"
		));
		airplaneApi.postNewAirplane(new AirplaneNewDto(
				"Airbus A310",
				262,
				5550.0,
				"al2"
		));
		airplaneApi.postNewAirplane(new AirplaneNewDto(
				"Airbus A350",
				325,
				9250.0,
				"al3"
		));
		airplaneApi.postNewAirplane(new AirplaneNewDto(
				"Boeing 717",
				235,
				3815.0,
				"al3"
		));
		airplaneApi.postNewAirplane(new AirplaneNewDto(
				"Airbus A318",
				132,
				5700.0,
				"al3"
		));
		airplaneApi.postNewAirplane(new AirplaneNewDto(
				"Boeing 737",
				215,
				3440.0,
				"al3"
		));

	}

	private void initAirport() {
		airportApi.postNewAirport(new AirportNewDto(
				"Lotnisko Chopina w Warszawie",
				21,
				52.168479,
				20.967150,
				"ap1"
		));
		airportApi.postNewAirport(new AirportNewDto(
				"Port lotniczy Kraków-Balice im. Jana Pawła II",
				11,
				50.076583,
				19.785689,
				"ap2"
		));
		airportApi.postNewAirport(new AirportNewDto(
				"Port lotniczy Gdańsk-Rębiechowo im. Lecha Wałęsy",
				8,
				54.380237,
				18.465248,
				"ap3"
		));
		airportApi.postNewAirport(new AirportNewDto(
				"Port lotniczy Rzeszów-Jasionka",
				4,
				50.114737,
				22.023160,
				"ap4"
		));
	}

	private void initAirline() {
		airlineApi.postNewAirline(new AirlineNewDto("Delta Air Lines", "al1"));
		airlineApi.postNewAirline(new AirlineNewDto("Ryanair", "al2"));
		airlineApi.postNewAirline(new AirlineNewDto("Lufthansa", "al3"));
	}

	private void initUser() {
		//region users
		adminApi.changeRole((
				userCreator.create(
						new UserChangeDto(
								"user1",
								"password",
								"user1@gmail.com"
						))), "USER");
		adminApi.changeRole((
				userCreator.create(
						new UserChangeDto(
								"user2",
								"password",
								"user2@gmail.com"
						))), "USER");
		adminApi.changeRole((
				userCreator.create(
						new UserChangeDto(
								"user3",
								"password",
								"user3@gmail.com"
						))), "USER");
		adminApi.changeRole((
				userCreator.create(
						new UserChangeDto(
								"user4",
								"password",
								"user4@gmail.com"
						))), "USER");
		//endregion
		//region airline owners
		adminApi.changeRole((
				userCreator.create(
						new UserChangeDto(
								"al1",
								"password",
								"al1@gmail.com"
						))), "AIRLINE_OWNER");
		adminApi.changeRole((
				userCreator.create(
						new UserChangeDto(
								"al2",
								"password",
								"al2@gmail.com"
						))), "AIRLINE_OWNER");
		adminApi.changeRole((
				userCreator.create(
						new UserChangeDto(
								"al3",
								"password",
								"al3@gmail.com"
						))), "AIRLINE_OWNER");
		//endregion
		//region airport owners
		adminApi.changeRole((
				userCreator.create(
						new UserChangeDto(
								"ap1",
								"password",
								"ap1@init.com"
						))), "AIRPORT_OWNER");
		adminApi.changeRole((
				userCreator.create(
						new UserChangeDto(
								"ap2",
								"password",
								"ap2@init.com"
						))), "AIRPORT_OWNER");
		adminApi.changeRole((
				userCreator.create(
						new UserChangeDto(
								"ap3",
								"password",
								"ap3@init.com"
						))), "AIRPORT_OWNER");
		adminApi.changeRole((
				userCreator.create(
						new UserChangeDto(
								"ap4",
								"password",
								"ap4@init.com"
						))), "AIRPORT_OWNER");
		//rendregion
		//region admins
		adminApi.changeRole((
				userCreator.create(
						new UserChangeDto(
								"admin",
								"password",
								"admin@init.com"
						))), "ADMIN");
		//endregion
	}
}

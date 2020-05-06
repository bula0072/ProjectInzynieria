package com.example.project;

import com.example.project.entities.Airplane;
import com.example.project.entities.users.Admin;
import com.example.project.entities.users.Airline;
import com.example.project.entities.users.Airport;
import com.example.project.entities.users.User;
import com.example.project.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;

@SpringBootApplication
public class ProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjectApplication.class, args);
    }

    @Bean
    public CommandLineRunner load(AirlineRepository airlineRepository,
                                  AirportRepository airportRepository,
                                  UserRepository userRepository,
                                  AirplaneRepository airplaneRepository,
                                  FlightRepository flightRepository,
                                  AdminRepository adminRepository) {
        return (args -> {
            try {
                airlineRepository.save(new Airline("User1", "Password1", "email@email.com", "Lotnisko we włoszczowie"));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            try {
                userRepository.save(new User("User2", "Password", "email2@email.com", "name", "surname", LocalDate.now()));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            try {
                airportRepository.save(new Airport("apoLogin", "Password3", "apoemail@ape.pl", "Szopena", 30, 30d, 20d));
                airportRepository.save(new Airport("apo2Login", "Password", "modlin@warszawa.pl", "Warszawa-Modlin", 30, 52.449912, 20.642579));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            try {
                airplaneRepository.save(new Airplane("Name", 50, 50d, airlineRepository.findAll().get(0)));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            airlineRepository.save(new Airline("AirloneOwner2", "Password1", "email@email.com", "Air line owner 2"));
            airplaneRepository.save(new Airplane("Name2", 50, 50d, airlineRepository.findAll().get(1)));
            adminRepository.save(new Admin("Adminlogin", "passwordadmin", "Admin@email.com"));
        });
    }
}

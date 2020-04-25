package com.example.project;

import com.example.project.entities.Airplane;
import com.example.project.entities.Flight;
import com.example.project.entities.users.AirlineOwner;
import com.example.project.entities.users.AirportOwner;
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
    public CommandLineRunner load(AirlineOwnerRepository airlineOwnerRepository,
                                  AirportOwnerRepository airportOwnerRepository,
                                  UserRepository userRepository,
                                  AirplaneRepository airplaneRepository,
                                  FlightRepository flightRepository) {
        return (args -> {
            airlineOwnerRepository.save(new AirlineOwner("User1", "Password1", "email@email.com", "Lotnisko we włoszczowie"));
            userRepository.save(new User("User2", "Password", "email2@email.com", "name", "surname", LocalDate.now()));
            airportOwnerRepository.save(new AirportOwner("apoLogin", "Password3", "apoemail@ape.pl", "Szopena", 30, 30d, 20d));
            try{
            airplaneRepository.save(new Airplane("Name", 50, 50d, airlineOwnerRepository.findAll().get(0)));
            airplaneRepository.save(new Airplane("Name2", 50, 50d, airlineOwnerRepository.findAll().get(0)));
            }catch (Exception e){
                System.out.println("Jest źle, nie było wcześniej mi tak\n" +
                        "W kręgosłup jakiś ból mnie wszedł i boleśnie tkwi tam");
            }
            try{
                flightRepository.save(new Flight(LocalDate.now(),
                        LocalDate.now(),
                        airportOwnerRepository.findAll().get(0),
                        airportOwnerRepository.findAll().get(0),
                        airplaneRepository.findAll().get(0),
                        50d));
            }catch (Exception e){
                System.out.println("\n" +
                        "    @OneToMany\n" +
                        "    private Set<Airplane> airplanes;\n");
            }
        });
    }
}

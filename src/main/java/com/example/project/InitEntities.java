package com.example.project;

import com.example.project.entity.*;
import com.example.project.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class InitEntities implements CommandLineRunner {
    @Autowired
    UserRepository userRepository;
    @Autowired
    AirlineRepository airlineRepository;
    @Autowired
    AirportRepository airportRepository;
    @Autowired
    AirplaneRepository airplaneRepository;

    @Override
    public void run(String... args) throws Exception {
        initUser();
        initAirline();
        initAirport();
        initAirplanes();
    }

    private void initAirplanes() {
     /*   airplaneRepository.save(new Airplane("Airplane", 50, 200D, userRepository.findByAccountType_Type("Airline Owner").get(0)));
        airplaneRepository.save(new Airplane("Airplane2", 50, 200D, userRepository.findByAccountType_Type("Airline Owner").get(1)));
   */ }

    private void initAirport() {
       /* airportRepository.save(new Airport("Airport", 40, 40D, 30D, true, userRepository.findByAccountType_Type("Airport Owner").get(0)));
        airportRepository.save(new Airport("Airport2", 100, 43.555D, 10.234D, true, userRepository.findByAccountType_Type("Airport Owner").get(1)));
   */ }

    private void initAirline() {
      /*  airlineRepository.save(new Airline("Airline", userRepository.findByAccountType_Type("Airline Owner").get(0)));
        airlineRepository.save(new Airline("Airline2", userRepository.findByAccountType_Type("Airline Owner").get(1)));*/
    }

    private void initUser() {
        userRepository.save(new User(
                "Userlogin",
                "Password",
                "email@gmail.com"));
        userRepository.save(new User(
                "Userlogin2",
                "Password2",
                "email2@gmail.com"));
        userRepository.save(new User(
                "airportllogin",
                "Password",
                "emaadfril@gmail.com",
                "AIRPORT_OWNER"));
        userRepository.save(new User(
                "airport2login",
                "Password",
                "emasdfsaail@gmail.com",
                "AIRPORT_OWNER"));
        userRepository.save(new User(
                "airlinelogin",
                "Passaisdfword",
                "emaasfdil@gmail.com",
                "AIRLINE_OWNER"));
        userRepository.save(new User(
                "airline2login",
                "Password",
                "emasdfaail@gmail.com",
                "AIRLINE_OWNER"));
        userRepository.save(new User(
                "Admin",
                "Adminpassword",
                "adminadmin@gmail.com",
                "ADMIN"));
    }
}

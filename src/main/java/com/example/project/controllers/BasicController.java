package com.example.project.controllers;

import com.example.project.entities.users.Admin;
import com.example.project.entities.users.AirportOwner;
import com.example.project.repositories.AdminRepository;
import com.example.project.repositories.AirportOwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BasicController {
    /*
    GetMapping()
    PostMapping()
    PutMapping()
    Deletemaping()
     */
    @Autowired
    AirportOwnerRepository airportOwnerRepository;
    @Autowired
    AdminRepository adminRepository;

    @GetMapping("/post")
    List<Admin> postAdmin(@RequestParam("login") String login, @RequestParam("password") String password, @RequestParam("email") String email) {
        Admin newAdmin = new Admin(login, password, email);
        adminRepository.save(newAdmin);
        return adminRepository.findAll();
    }
}

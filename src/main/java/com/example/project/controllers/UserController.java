package com.example.project.controllers;

import com.example.project.dto.UserDto1;
import com.example.project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {
    @Autowired
    UserRepository userRepository;

    @GetMapping("users")
    List<UserDto1> getAllUsers() {
        List<UserDto1> userDto1s = new ArrayList<>();
        userRepository.findAll().forEach(user -> userDto1s.add(new UserDto1(user)));
        return userDto1s;
    }

/*    @GetMapping("users/{id}")
    UserDto2 getUserById(
            @PathVariable(name = "id") Long id
    ) {
        try {
            return new UserDto2(userRepository.findById(id).orElseThrow());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return new UserDto2();
    }*/

   /* @PostMapping("users")
    List<UserDto1> postNewUser(
            @RequestParam(name = "login") String login,
            @RequestParam(name = "password") String password,
            @RequestParam(name = "email") String email,
            @RequestParam(name = "account-type", required = false) String accountType
    ) {
        User user = new User(
                login,
                password,
                email,
                accountType == null ? accountTypeRepository.findByType("User") : accountTypeRepository.findByType(accountType)
        );
        userRepository.save(user);
        return getAllUsers();
    }*/

    @DeleteMapping("users/{id}")
    List<UserDto1> deleteUser(
            @PathVariable(name = "id") Long id
    ) {
        userRepository.deleteById(id);
        return getAllUsers();
    }

    /*@PatchMapping("users/{id}")
    UserDto2 patchUser(
            @PathVariable(name = "id") Long id,
            @RequestParam(name = "login", required = false) String login,
            @RequestParam(name = "password", required = false) String password,
            @RequestParam(name = "email", required = false) String email,
            @RequestParam(name = "account-type", required = false) String accountType
    ) {
        User user = userRepository.findById(id).orElseThrow();

        if (login != null) user.setLogin(login);
        if (password != null) user.setPassword(password);
        if (email != null) user.setEmail(email);
        if (accountType != null) user.setAccountType(accountTypeRepository.findByType(accountType));

        return new UserDto2(userRepository.save(user));
    }*/
}

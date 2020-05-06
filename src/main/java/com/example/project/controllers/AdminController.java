package com.example.project.controllers;

import com.example.project.dto.admins.AdminDTO;
import com.example.project.entities.users.Admin;
import com.example.project.interfaces.IInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class AdminController extends BasicController {
    @Autowired
    AdminDTO adminDTO;

    @GetMapping("admins")
    List<IInfo> getAllAdmins() {
        return adminRepository.findAll().stream().map(adminDTO::getBasic).collect(Collectors.toList());
    }

    @GetMapping("admins/{id}")
    IInfo getAdminById(
            @PathVariable(name = "id") Long id,
            @RequestParam(name = "token", required = false) Long token
    ) {
        try {
            if (adminRepository.findById(id).isEmpty()) throw new NullPointerException();
            Admin admin = adminRepository.findById(id).get();
            if (tokenIsNullOrNotEquals(token, id)) {
                return adminDTO.getBasic(admin);
            }
            return adminDTO.getOwner(admin);
        } catch (NullPointerException exception) {
            System.out.println(exception.getMessage());
            return adminDTO.getFail();
        }
    }

    @PostMapping("admins")
    IInfo postNewAdmin(
            @RequestParam(name = "token") Long token,
            @RequestParam(name = "login") String login,
            @RequestParam(name = "password") String password,
            @RequestParam(name = "email") String email
    ) {
        if (tokenBelongsToAdmin(token))
            return adminDTO.getOwner(adminRepository.save(new Admin(login, password, email)));
        return adminDTO.getFail();
    }

    @PatchMapping("admins/{id}")
    IInfo patchAdmin(
            @PathVariable(name = "id") Long id,
            @RequestParam(name = "token", required = false) Long token,
            @RequestParam(name = "login", required = false) String login,
            @RequestParam(name = "password", required = false) String password,
            @RequestParam(name = "email", required = false) String email
    ) {
        try {
            if (adminRepository.findById(id).isEmpty() || !tokenBelongsToAdmin(token)) throw new Exception();
            Admin admin = adminRepository.findById(id).get();

            if (login != null) admin.setLogin(login);
            if (password != null) admin.setPassword(password);
            if (email != null) admin.setEmail(email);

            return adminDTO.getOwner(adminRepository.save(admin));
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
        return adminDTO.getFail();
    }

    @DeleteMapping("admins/{id}")
    List<IInfo> deleteAdmin(
            @PathVariable(name = "id") Long id,
            @RequestParam(name = "token") Long token
    ) {
        try {
            if (!tokenBelongsToAdmin(token) || adminRepository.findById(id).isEmpty()) throw new Exception();
            adminRepository.delete(adminRepository.findById(id).get());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return getAllAdmins();
    }
}

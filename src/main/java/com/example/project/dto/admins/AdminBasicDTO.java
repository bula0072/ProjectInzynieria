package com.example.project.dto.admins;

import com.example.project.entities.users.Admin;
import com.example.project.interfaces.IInfo;
import lombok.Data;

@Data
public class AdminBasicDTO implements IInfo {
    private Long id;
    private String login;

    public AdminBasicDTO(Admin admin) {
        this.id = admin.getId();
        this.login = admin.getLogin();
    }
}

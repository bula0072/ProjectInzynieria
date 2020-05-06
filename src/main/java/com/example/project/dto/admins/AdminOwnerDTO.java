package com.example.project.dto.admins;

import com.example.project.entities.users.Admin;
import lombok.Data;

@Data
public class AdminOwnerDTO extends AdminBasicDTO {
    private String email;

    public AdminOwnerDTO(Admin admin) {
        super(admin);
        this.email = admin.getEmail();
    }
}

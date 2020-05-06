package com.example.project.dto.admins;

import com.example.project.entities.users.Admin;
import com.example.project.interfaces.IGet;
import com.example.project.interfaces.IInfo;
import org.springframework.stereotype.Service;

@Service
public class AdminDTO implements IGet<Admin> {
    @Override
    public IInfo getBasic(Admin admin) {
        return new AdminBasicDTO(admin);
    }

    @Override
    public IInfo getOwner(Admin admin) {
        return new AdminOwnerDTO(admin);
    }
}

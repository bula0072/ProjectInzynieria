package com.example.project.dto;

import com.example.project.interfaces.IInfo;

public class FailDTO implements IInfo {
    @Override
    public Boolean getStatus() {
        return false;
    }
}

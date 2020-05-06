package com.example.project.interfaces;

public interface IInfo {
    default Boolean getStatus() {
        return true;
    }
}

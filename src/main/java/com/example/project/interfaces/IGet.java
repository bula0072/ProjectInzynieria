package com.example.project.interfaces;

import com.example.project.dto.FailDTO;

public interface IGet<Entity> {
    IInfo getBasic(Entity entity);

    IInfo getOwner(Entity entity);

    default IInfo getFail() {
        return new FailDTO();
    }
}

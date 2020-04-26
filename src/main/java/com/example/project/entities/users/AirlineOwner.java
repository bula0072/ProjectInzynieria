package com.example.project.entities.users;

import com.example.project.entities.Airplane;
import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Data
@Entity
public class AirlineOwner extends AbstractUser {
    private String name;
    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Airplane> airplanes;

    protected AirlineOwner() {
    }

    public AirlineOwner(String login, String password, String email, String name) {
        super(login, password, email);
        this.name = name;
    }
}

package com.example.project.entities.users;

import com.example.project.entities.Airplane;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.Set;

@Data
@Entity
public class AirlineOwner extends AbstractUser {
    private String airlineName;

    protected AirlineOwner(){}
    public AirlineOwner(String login, String password, String email, String airlineName) {
        super(login, password, email);
        this.airlineName = airlineName;
    }
}

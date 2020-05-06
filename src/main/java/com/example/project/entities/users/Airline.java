package com.example.project.entities.users;

import com.example.project.entities.Airplane;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class Airline extends AbstractUser {
    private String name;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Airplane> airplanes;

    public Airline() {
    }

    public Airline(String login, String password, String email, String name) {
        super(login, password, email);
        this.name = name;
    }
}

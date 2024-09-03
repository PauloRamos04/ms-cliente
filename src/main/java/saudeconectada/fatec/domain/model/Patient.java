package saudeconectada.fatec.domain.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "patient")
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String cpf;
    private String email;
    private String phone;
    private String address;
    private Gender gender;
    private LocalDate birthDate;
    private String deficiency;
    private String photo;
    private String password;

}

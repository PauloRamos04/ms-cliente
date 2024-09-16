package saudeconectada.fatec.domain.model;

import jakarta.persistence.*;
import lombok.Data;
import saudeconectada.fatec.domain.enums.Deficiency;
import saudeconectada.fatec.domain.enums.Gender;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Entity
@Table(name = "patient")
public class Patient implements Verifiable { // Implementando a interface
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String password;
    private String cpf;
    private String email;
    private String phone;
    private String address;
    private Gender gender;
    private LocalDate birthDate;
    private Deficiency deficiency;
    private String photo;
    private UUID verificationToken;
    private boolean verified = false;

    @Override
    public boolean isVerified() {
        return verified;
    }
}

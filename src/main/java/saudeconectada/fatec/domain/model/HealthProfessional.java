package saudeconectada.fatec.domain.model;

import jakarta.persistence.*;
import lombok.Data;
import saudeconectada.fatec.domain.enums.Gender;
import saudeconectada.fatec.domain.enums.ProfessionalType;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "health_professional")
public class HealthProfessional implements Verifiable { // Implementando a interface

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String password;
    private String cpf;
    private String email;
    private String healthUnitNumber;
    private String phone;
    private String address;
    private LocalDate birthDate;
    private Gender gender;
    private ProfessionalType professionalType;
    private boolean verified = true;

    @Override
    public boolean isVerified() {
        return verified;
    }
}

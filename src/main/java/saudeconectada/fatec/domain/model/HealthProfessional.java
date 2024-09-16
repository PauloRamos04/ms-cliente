package saudeconectada.fatec.domain.model;

import jakarta.persistence.*;
import lombok.Data;
import saudeconectada.fatec.domain.enums.Deficiency;
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
    private boolean verified = true;
    private ProfessionalType professionalType;
    private String healthUnitNumber;

    @Override
    public boolean isVerified() {
        return verified;
    }
}

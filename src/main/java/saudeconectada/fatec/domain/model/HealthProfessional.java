package saudeconectada.fatec.domain.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Setter;
import saudeconectada.fatec.domain.enums.Deficiency;
import saudeconectada.fatec.domain.enums.Gender;
import saudeconectada.fatec.domain.enums.ProfessionalType;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

@Data
@Entity
@Table(name = "health_professional")
public class HealthProfessional implements Verifiable {

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
    private Date birthDate;
    private Deficiency deficiency;
    private String photo;
    private ProfessionalType professionalType;
    @Setter
    private UUID verificationToken;
    @Setter
    private boolean verified = false;
    private String healthUnitNumber;

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public UUID getVerificationToken() {
        return verificationToken;
    }

    @Override
    public boolean isVerified() {
        return verified;
    }

}

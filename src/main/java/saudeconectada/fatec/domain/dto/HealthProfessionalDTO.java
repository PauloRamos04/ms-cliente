package saudeconectada.fatec.domain.dto;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import saudeconectada.fatec.domain.model.Gender;
import saudeconectada.fatec.domain.model.ProfessionalType;

import java.time.LocalDate;

@Getter
@Setter
public class HealthProfessionalDTO {

    @NotNull
    @Size(min = 3, max = 50)
    private String name;

    @NotNull
    @Size(min = 8, max = 20)
    private String password;

    @NotNull
    @Size(min = 11, max = 11)
    @Column(unique = true)
    private String cpf;

    @NotNull
    @Email
    @Size(max = 100)
    @Column(unique = true)
    private String email;

    @NotNull
    @Size(max = 50)
    private String healthUnitNumber;

    @Size(max = 15)
    private String phone;

    @NotNull
    private String address;

    @NotNull
    private LocalDate birthDate;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @NotNull
    @Enumerated
    private ProfessionalType professionalType;

}

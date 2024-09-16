package saudeconectada.fatec.domain.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import saudeconectada.fatec.domain.enums.Gender;
import saudeconectada.fatec.domain.enums.ProfessionalType;

import java.time.LocalDate;

@Getter
@Setter
public class HealthProfessionalDTO {

    @NotBlank(message = "O primeiro nome não deve ser nulo")
    @Size(min = 3, max = 50)
    private String firstName;

    @NotBlank(message = "O sobrenome não deve ser nulo")
    @Size(min = 3, max = 50)
    private String lastName;

    @NotBlank(message = "A senha não deve ser nula")
    @Size(min = 8, max = 20, message = "Tamanho da senha deve ser entre 8 e 20")
    private String password;

    @NotBlank(message = "O CPF não deve ser nulo")
    @Pattern(regexp = "\\d{11}", message = "O CPF deve conter 11 dígitos, sem caracteres especiais.")
    private String cpf;

    @NotBlank(message = "O email não deve ser nulo")
    @Email(message = "Email deve ser válido")
    @Size(max = 100)
    private String email;

    @NotBlank(message = "O número da unidade de saúde não deve ser nulo")
    @Size(max = 50)
    private String healthUnitNumber;

    @Size(max = 15)
    private String phone;

    @NotBlank(message = "O endereço não deve ser nulo")
    private String address;

    @NotNull(message = "A data de nascimento não deve ser nula")
    private LocalDate birthDate;

    @NotNull(message = "O gênero não deve ser nulo")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @NotNull(message = "O tipo profissional não deve ser nulo")
    @Enumerated(EnumType.STRING)
    private ProfessionalType professionalType;

    private String photo;
}

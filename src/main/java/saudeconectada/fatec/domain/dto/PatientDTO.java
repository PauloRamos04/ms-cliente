package saudeconectada.fatec.domain.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import saudeconectada.fatec.domain.enums.Deficiency;
import saudeconectada.fatec.domain.enums.Gender;

import java.time.LocalDate;

@Getter
@Setter
public class PatientDTO {

    @NotBlank(message = "O primeiro nome não deve ser nulo")
    private String firstName;

    @NotBlank(message = "O sobrenome não deve ser nulo")
    private String lastName;

    @NotBlank(message = "O CPF não deve ser nulo")
    @Pattern(regexp = "\\d{11}", message = "O CPF deve conter 11 dígitos, sem caracteres especiais.")
    private String cpf;

    @NotBlank(message = "O email não deve ser nulo")
    @Email(message = "Email deve ser válido")
    private String email;

    @NotBlank(message = "O telefone não deve ser nulo")
    private String phone;

    @NotBlank(message = "O endereço não deve ser nulo")
    private String address;

    @NotNull(message = "O gênero não deve ser nulo")
    private Gender gender;

    @NotNull(message = "A data de nascimento não deve ser nula")
    private LocalDate birthDate;

    @NotNull(message = "A deficiência não pode ser nula")
    private Deficiency deficiency;

    private String photo;

    @Size(min = 8, max = 20, message = "Tamanho da senha deve ser entre 8 e 20")
    private String password;
}

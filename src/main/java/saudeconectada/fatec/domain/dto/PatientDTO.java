package saudeconectada.fatec.domain.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import saudeconectada.fatec.domain.enums.Deficiency;
import saudeconectada.fatec.domain.enums.Gender;
import saudeconectada.fatec.domain.model.Patient;

import java.time.LocalDate;

@Getter
@Setter
public class PatientDTO {

    @NotBlank(message = "O primeiro nome não deve ser nulo")
    @Size(min = 2, max = 50, message = "O primeiro nome deve ter entre 2 e 50 caracteres")
    private String firstName;

    @NotBlank(message = "O sobrenome não deve ser nulo")
    @Size(min = 2, max = 50, message = "O sobrenome deve ter entre 2 e 50 caracteres")
    private String lastName;

    @NotBlank(message = "O CPF não deve ser nulo")
    @Pattern(regexp = "\\d{11}", message = "O CPF deve conter 11 dígitos, sem caracteres especiais")
    private String cpf;

    @NotBlank(message = "O email não deve ser nulo")
    @Email(message = "O email deve ser válido")
    @Size(max = 100, message = "O email deve ter no máximo 100 caracteres")
    private String email;

    @NotBlank(message = "O telefone não deve ser nulo")
    @Pattern(regexp = "\\d{10,11}", message = "O telefone deve conter entre 10 e 11 dígitos")
    private String phone;

    @NotBlank(message = "O endereço não deve ser nulo")
    @Size(max = 200, message = "O endereço deve ter no máximo 200 caracteres")
    private String address;

    @NotNull(message = "O gênero não deve ser nulo")
    private Gender gender;

    @NotNull(message = "A data de nascimento não deve ser nula")
    @Past(message = "A data de nascimento deve ser no passado")
    private LocalDate birthDate;

    @NotNull(message = "A deficiência não pode ser nula")
    private Deficiency deficiency;

    @Size(max = 255, message = "A foto deve ter no máximo 255 caracteres")
    private String photo;

    @NotBlank(message = "A senha não deve ser nula")
    @Size(min = 8, max = 20, message = "A senha deve ter entre 8 e 20 caracteres")
    private String password;
}

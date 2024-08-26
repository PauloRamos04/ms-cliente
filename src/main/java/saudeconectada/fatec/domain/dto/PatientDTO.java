package saudeconectada.fatec.domain.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import saudeconectada.fatec.domain.model.Gender;

import java.time.LocalDate;

@Getter
@Setter
public class PatientDTO {
    @NotBlank(message = "O primeiro nome não deve ser nulo")
    private String firstName;

    @NotBlank(message = "O sobrenome não deve ser nulo")
    private String lastName;

    @NotBlank(message = "O CPF não deve ser nulo")
    private String cpf;

    @Email(message = "Email deve ser válido")
    @NotBlank(message = "O email não deve ser nulo")
    private String email;

    @NotBlank(message = "O telefone não deve ser nulo")
    private String phone;

    @NotBlank(message = "O endereço não deve ser nulo")
    private String address;

    @NotNull(message = "O gênero não deve ser nulo")
    private Gender gender;

    @NotNull(message = "A data de nascimento não deve ser nula")
    private LocalDate birthDate;

    private String deficiency;
    private String photo;

    @Size(min = 8, max = 20, message = "Tamanho deve ser entre 8 e 20")
    private String password;
}

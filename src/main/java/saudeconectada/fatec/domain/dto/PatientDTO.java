package saudeconectada.fatec.domain.dto;

import lombok.Data;
import java.time.LocalDate;
import java.util.Set;

@Data
public class PatientDTO {
    private String firstName;
    private String lastName;
    private String cpf;
    private String email;
    private String phone;
    private String address;
    private String gender; // String para receber o valor do JSON
    private LocalDate birthDate;
    private String deficiency; // String para receber o valor do JSON
    private String photo;
    private String password;
    private Set<ChildDTO> children; // Lista de filhos
}

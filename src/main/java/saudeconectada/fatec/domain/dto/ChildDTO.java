package saudeconectada.fatec.domain.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class ChildDTO {
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String gender; // Ajuste conforme necessário
}

package saudeconectada.fatec.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "health_units")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HealthUnit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 3, max = 100)
    private String nameUnit;

    @Size(max = 50)
    private String unitNumber;

    @Size(min = 14, max = 18)
    private String cnpj;

    @Size(min = 8, max = 20)
    private String password;

    @Email
    @Size(max = 100)
    private String email;
}


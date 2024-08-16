package saudeconectada.fatec.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "health_professionals")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "professional_type")
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class HealthProfessional {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 3, max = 50)
    private String name;

    @NotNull
    @Size(min = 8, max = 20)
    private String password;

    @NotNull
    @Size(min = 11, max = 11) // CPF deve ter exatamente 11 caracteres
    @Column(unique = true)
    private String cpf;

    @NotNull
    @Email
    @Size(max = 100)
    @Column(unique = true)
    private String email;

    @NotNull
    @Size(max = 50)
    private String healthUnitNumber; // Número da unidade de saúde

    @Size(max = 15)
    private String phone;

    @NotNull
    private String address;

    @NotNull
    private LocalDate birthDate;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @OneToMany(mappedBy = "healthProfessional", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<LoginHistory> loginHistory = new HashSet<>();

    // Métodos para gerenciar o histórico de login
    public void addLoginHistory(LoginHistory history) {
        loginHistory.add(history);
        history.setHealthProfessional(this);
    }

    public void removeLoginHistory(LoginHistory history) {
        loginHistory.remove(history);
        history.setHealthProfessional(null);
    }
}
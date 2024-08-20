package saudeconectada.fatec.domain.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "patients")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 3, max = 50)
    private String firstName;

    @NotNull
    @Size(min = 3, max = 50)
    private String lastName;

    @NotNull
    @Size(min = 11, max = 11)
    @Column(unique = true)
    private String cpf;

    @Email
    @Size(max = 100)
    @Column(unique = true)
    private String email;

    @Size(max = 15)
    private String phone;

    @NotNull
    @Column(nullable = false)
    private String address;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @NotNull
    private LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    private Deficiency deficiency;

    private String photo;

    @NotNull
    @Size(min = 8, max = 20)
    private String password;

    @JsonManagedReference // Evita recursão na serialização
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Child> children = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Patient)) return false;
        Patient patient = (Patient) o;
        return Objects.equals(cpf, patient.cpf);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cpf); // Apenas o CPF para evitar recursão
    }
}

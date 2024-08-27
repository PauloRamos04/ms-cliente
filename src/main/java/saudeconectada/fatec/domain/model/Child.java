package saudeconectada.fatec.domain.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "children")
@Data
public class Child {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Patient parent;

    public Child() {}

    public Child(String firstName, String lastName, LocalDate birthDate, Gender gender, Patient parent) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.gender = gender;
        this.parent = parent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Child)) return false;
        Child child = (Child) o;
        return Objects.equals(firstName, child.firstName) &&
                Objects.equals(lastName, child.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName); // Apenas o nome para evitar recursão
    }
}

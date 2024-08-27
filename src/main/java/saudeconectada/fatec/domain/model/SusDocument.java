package saudeconectada.fatec.domain.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "sus_documents")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SusDocument {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String documentType;

    @NotNull
    private String documentNumber;

    private LocalDate issueDate;

    private LocalDate expirationDate;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;
}

package saudeconectada.fatec.domain.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("TECHNICIAN")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NursingTechnician extends HealthProfessional {

    @NotNull
    @Size(max = 20)
    private String coren; // Número do COREN
}
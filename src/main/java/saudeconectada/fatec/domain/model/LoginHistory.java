package saudeconectada.fatec.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "login_history")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private LocalDateTime loginTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "health_professional_id", nullable = false)
    private HealthProfessional healthProfessional;
}
package saudeconectada.fatec.repository;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import saudeconectada.fatec.domain.model.HealthProfessional;

@Repository
public interface HealthProfessionalRepository extends JpaRepository<HealthProfessional, Long> {
    HealthProfessional findByCpf(String cpf);

    boolean existsByCpf(String cpf);
}

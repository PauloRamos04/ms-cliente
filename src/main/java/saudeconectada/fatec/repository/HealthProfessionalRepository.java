package saudeconectada.fatec.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import saudeconectada.fatec.domain.model.HealthProfessional;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface HealthProfessionalRepository extends JpaRepository<HealthProfessional, Long>, UserRepository<HealthProfessional> {
    HealthProfessional findByCpf(String cpf);
    boolean existsByCpf(String cpf);
    Optional<HealthProfessional> findByVerificationToken(UUID token);
}

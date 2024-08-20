package saudeconectada.fatec.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import saudeconectada.fatec.domain.model.HealthProfessional;

import java.util.Optional;

@Repository
public interface HealthProfessionalRepository extends JpaRepository<HealthProfessional, Long> {

    Optional<HealthProfessional> findByCpf(String cpf);

    Optional<HealthProfessional> findByEmail(String email);

    boolean existsByCpf(String cpf);

    boolean existsByEmail(String email);
}

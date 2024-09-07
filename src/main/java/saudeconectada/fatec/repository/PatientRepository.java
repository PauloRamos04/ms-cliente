package saudeconectada.fatec.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import saudeconectada.fatec.domain.model.Patient;

import java.util.UUID;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    Patient findByCpf(String cpf);
    boolean existsByCpf(String cpf);
    Patient findByVerificationToken(UUID token);
}

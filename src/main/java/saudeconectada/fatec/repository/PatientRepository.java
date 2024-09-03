package saudeconectada.fatec.repository;

import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import saudeconectada.fatec.domain.model.Patient;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    Patient findByCpf(String cpf);

    boolean existsByCpf(String cpf);
}

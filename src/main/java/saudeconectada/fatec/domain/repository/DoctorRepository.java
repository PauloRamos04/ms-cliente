package saudeconectada.fatec.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import saudeconectada.fatec.domain.model.Doctor;

import java.util.Optional;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    Optional<Doctor> findByCrm(String crm);
}
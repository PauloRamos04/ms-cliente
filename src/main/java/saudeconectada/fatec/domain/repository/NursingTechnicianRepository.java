package saudeconectada.fatec.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import saudeconectada.fatec.domain.model.NursingTechnician;

import java.util.Optional;

public interface NursingTechnicianRepository extends JpaRepository<NursingTechnician, Long> {

    Optional<NursingTechnician> findByCoren(String coren);
}

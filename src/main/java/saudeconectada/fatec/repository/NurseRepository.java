package saudeconectada.fatec.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import saudeconectada.fatec.domain.model.Nurse;

import java.util.Optional;

@Repository
public interface NurseRepository extends JpaRepository<Nurse, Long> {

    Optional<Nurse> findByCoren(String coren);
}

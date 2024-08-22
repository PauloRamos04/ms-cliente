package saudeconectada.fatec.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import saudeconectada.fatec.domain.model.HealthUnit;

@Repository
public interface HealthUnitRepository extends JpaRepository<HealthUnit, Long> {
}

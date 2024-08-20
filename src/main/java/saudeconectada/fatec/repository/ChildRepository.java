package saudeconectada.fatec.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import saudeconectada.fatec.domain.model.Child;

public interface ChildRepository extends JpaRepository<Child, Long> {
}

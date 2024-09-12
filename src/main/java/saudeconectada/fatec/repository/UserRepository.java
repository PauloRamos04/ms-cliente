package saudeconectada.fatec.repository;

import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository<T> {
    T findByCpf(String cpf);
}

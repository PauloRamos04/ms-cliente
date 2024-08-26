package saudeconectada.fatec.domain.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String cpf;
    private String password;
}
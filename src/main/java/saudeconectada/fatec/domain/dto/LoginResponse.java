package saudeconectada.fatec.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {
    private String token;
    private String message; // Mensagem opcional, como "Login bem-sucedido"
}
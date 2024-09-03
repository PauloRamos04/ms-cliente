package saudeconectada.fatec.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import saudeconectada.fatec.domain.dto.LoginRequest;
import saudeconectada.fatec.domain.dto.LoginResponse;
import saudeconectada.fatec.service.UserService;

@RestController
public abstract class UserController<T> {

    protected abstract UserService<T> getUserService();

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginUser(@RequestBody LoginRequest loginRequest) {
        String token = getUserService().loginUser(loginRequest.getCpf(), loginRequest.getPassword());
        return ResponseEntity.ok(new LoginResponse(token, "Login Ok"));
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logoutUser(@RequestBody LoginRequest loginRequest) {
        getUserService().logoutUser(loginRequest.getCpf());
        return ResponseEntity.ok("Logout realizado com sucesso.");
    }
}

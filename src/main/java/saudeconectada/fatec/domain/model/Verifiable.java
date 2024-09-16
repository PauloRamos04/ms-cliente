package saudeconectada.fatec.domain.model;

import java.util.UUID;

public interface Verifiable {
    String getEmail();
    UUID getVerificationToken();
    boolean isVerified();
}

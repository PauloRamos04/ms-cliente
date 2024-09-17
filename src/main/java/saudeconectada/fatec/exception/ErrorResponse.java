package saudeconectada.fatec.exception;

import java.util.Map;

public record ErrorResponse(String message, String errorType, String errorCode, Map<String, String> details) {
}

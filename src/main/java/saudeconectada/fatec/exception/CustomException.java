package saudeconectada.fatec.exception;

public class CustomException extends RuntimeException {
    private final String errorMessage;
    private final String errorCode;

    public CustomException(String errorMessage, String errorCode) {
        super(errorMessage);
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }
}

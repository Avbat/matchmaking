package io.cell.matchmaking.utils;

public class VerificationResult {
    private String message;
    private boolean succes;

    public String getMessage() {
        return message;
    }

    public VerificationResult setMessage(String message) {
        this.message = message;
        return this;
    }

    public boolean isSucces() {
        return succes;
    }

    public VerificationResult setSucces(boolean succes) {
        this.succes = succes;
        return this;
    }
}

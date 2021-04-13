package io.cell.matchmaking.services;

public class RegistrationResult {
    private String message;
    private RegistrationStatuses status;

    public String getMessage() {
        return message;
    }

    public RegistrationResult setMessage(String message) {
        this.message = message;
        return this;
    }

    public RegistrationStatuses getStatus() {
        return status;
    }

    public RegistrationResult setStatus(RegistrationStatuses status) {
        this.status = status;
        return this;
    }

    public static RegistrationResultBuilder builder() {
        return new RegistrationResult().new RegistrationResultBuilder();
    }

    public class RegistrationResultBuilder {

        private RegistrationResultBuilder() {
        }

        public RegistrationResultBuilder withMessage(String message) {
            RegistrationResult.this.message = message;
            return this;
        }

        public RegistrationResultBuilder withStatus(RegistrationStatuses status) {
            RegistrationResult.this.status = status;
            return this;
        }

        public RegistrationResult build() {
            return RegistrationResult.this;
        }
    }
}

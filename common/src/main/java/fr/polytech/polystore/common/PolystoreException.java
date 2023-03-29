package fr.polytech.polystore.common;

public class PolystoreException extends Exception {
    protected String message;

    public PolystoreException(String message) {
        this.message = message;
    }

    public static class NotFound extends PolystoreException {
        public NotFound(String message) {
            super(message);
        }
    }

}

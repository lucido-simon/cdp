package fr.polytech.polystore.common;

public class PolystoreException extends Exception {
    protected String message;

    public PolystoreException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public static class NotFound extends PolystoreException {
        public NotFound(String message) {
            super(message);
        }
    }

    public static class Unknown extends PolystoreException {
        public Unknown(String message) {
            super(message);
        }
    }

    public static class NotEnoughStock extends PolystoreException {
        public NotEnoughStock(String message) {
            super(message);
        }
    }

}

package it.polimi.ingsw.client.view.exceptions;

public class invalidClientInputException extends Exception {
    private final String errorMessage;

    public invalidClientInputException(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}

package it.polimi.ingsw.server.controller.states.exceptions;

public class invalidMoveException extends Exception {
    private String errorMessage;

    public invalidMoveException(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}

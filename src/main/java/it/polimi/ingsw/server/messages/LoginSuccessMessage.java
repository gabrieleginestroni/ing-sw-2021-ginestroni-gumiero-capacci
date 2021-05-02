package it.polimi.ingsw.server.messages;

public class LoginSuccessMessage extends AnswerMessage {

    @Override
    public void selectView() {
        System.out.println("Login success.");
    }
}

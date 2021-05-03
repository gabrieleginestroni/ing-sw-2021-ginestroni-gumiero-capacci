package it.polimi.ingsw.client.network_handler;

import it.polimi.ingsw.client.view.CLI;
import it.polimi.ingsw.server.messages.client_server.LoginRequestMessage;
import it.polimi.ingsw.server.messages.client_server.LoginSizeMessage;
import it.polimi.ingsw.server.messages.server_client.*;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class NetworkHandlerCLI extends NetworkHandler {
    private final CLI view;

    public NetworkHandlerCLI(Socket socket, CLI view) {
        super(socket);
        this.view = view;
    }

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);

        view.showMessage("Type nickname:");
        String nickname = scanner.nextLine();
        view.showMessage("Type game ID:");
        String gameID = scanner.nextLine();

        try {
            output.writeObject(new LoginRequestMessage(gameID,nickname));
            AnswerMessage message;

            boolean loginStatus = true;
            while(loginStatus) {
                message = (AnswerMessage) input.readObject();
                message.selectView(view);

                if(message instanceof RequestLobbySizeMessage) {

                    output.writeObject(new LoginSizeMessage(scanner.nextInt()));
                }

                if(message instanceof LobbyFullMessage || message instanceof LobbyNotReadyMessage) {
                    gameID = scanner.nextLine();

                    output.writeObject(new LoginRequestMessage(gameID,nickname));
                }

                if(message instanceof LoginSuccessMessage) loginStatus = false;
            }

            //while(true) {

        //}
            socket.close();

        //boolean stop = false;
        //while (!stop) {}
        } catch(IOException e) { view.showMessage("Server unreachable");}
         catch (ClassNotFoundException e) { view.showMessage("Invalid stream");}




    }
}

package it.polimi.ingsw.client;

import it.polimi.ingsw.client.view.CLI;
import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.server.messages.client_server.LoginRequestMessage;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ClientCLI {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        View view = new CLI();
        //System.out.println("Insert server ip address:");
        //String ip = scanner.nextLine();
        //String ip = "94.176.46.205";
        String ip = "localhost";
        view.showMessage("Insert server port number:");
        int port = Integer.parseInt(scanner.nextLine().trim());

        try {
            Socket socket = new Socket(ip, port);
            System.out.println(socket);
            NetworkHandler networkHandler = new NetworkHandler(socket, view);
            view.addNetworkHandler(networkHandler);

            view.showMessage("Type game ID:");
            String gameID = scanner.nextLine();
            view.showMessage("Type nickname:");
            String nickname = scanner.nextLine();
            view.setNickname(nickname);
            networkHandler.sendMessage(new LoginRequestMessage(gameID,nickname));

            Thread networkThread = new Thread(networkHandler);
            networkThread.start();
            networkThread.join();

        } catch (IOException | InterruptedException e) {
            System.out.println("Server unreachable");
        }
    }
}

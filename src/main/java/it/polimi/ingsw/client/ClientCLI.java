package it.polimi.ingsw.client;

import it.polimi.ingsw.client.view.CLI;
import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.server.messages.client_server.LoginRequestMessage;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class ClientCLI {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CLI view = new CLI();
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
            String nickname;
            view.showMessage("Type nickname:");
            nickname = scanner.nextLine();
            while(nickname.length() > 20) {
                view.showMessage("Nickname must be < 20 chars");
                view.showMessage("Type nickname:");
                nickname = scanner.nextLine();
            }
            view.setNickname(nickname);
            networkHandler.sendMessage(new LoginRequestMessage(gameID,nickname));

            Thread networkThread = new Thread(networkHandler);
            networkThread.start();

            /*
            while(!view.isGameOver()){
                String input;
                scanner = new Scanner(System.in);
                TimeUnit.SECONDS.sleep(1);
                if(view.getCurrentPlayer() != null && !view.getCurrentPlayer().equals(view.getNickname())) {
                    input = scanner.nextLine();
                    if (!view.getCurrentPlayer().equals(view.getNickname()))
                        view.showMessage("It's " + view.getCurrentPlayer() + "'s turn, please wait yours.");
                    else{
                        String fakeInput = "fakeInput\n";
                        InputStream in = new ByteArrayInputStream(fakeInput.getBytes(StandardCharsets.UTF_8));
                        scanner = new Scanner(in);
                    }
                }
             }
             */

            networkThread.join();

        } catch (IOException | InterruptedException e) {
            System.out.println("Server unreachable");
        }
    }
}

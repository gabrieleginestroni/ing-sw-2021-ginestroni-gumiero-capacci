package it.polimi.ingsw.client;

import it.polimi.ingsw.client.network_handler.NetworkHandler;
import it.polimi.ingsw.client.view.CLI;
import it.polimi.ingsw.client.view.View;

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
        int port = scanner.nextInt();

        try {
            Socket socket = new Socket(ip, port);
            System.out.println(socket);
             NetworkHandler networkHandler = new NetworkHandler(socket, view);
             view.addNetworkHandler(networkHandler);

             Thread networkThread = new Thread();
             networkThread.start();
             networkThread.join();

        } catch (IOException | InterruptedException e) {
            System.out.println("Server unreachable");
        }
    }
}

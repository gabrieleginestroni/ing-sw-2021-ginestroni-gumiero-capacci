package it.polimi.ingsw.client;

import it.polimi.ingsw.client.network_handler.NetworkHandlerCLI;
import it.polimi.ingsw.client.view.CLI;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ClientCLI {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CLI view = new CLI();

        //System.out.println("Insert server ip address:");
        //String ip = scanner.nextLine();
        //String ip = "94.176.46.205";
        String ip = "localhost";
        view.showMessage("Insert server port number:");
        int port = scanner.nextInt();

        try {
            Socket socket = new Socket(ip, port);
            System.out.println(socket);

             Thread networkThread = new Thread(new NetworkHandlerCLI(socket, view));
             networkThread.start();
             networkThread.join();

        } catch (IOException | InterruptedException e) {
            System.out.println("Server unreachable");
        }
    }
}

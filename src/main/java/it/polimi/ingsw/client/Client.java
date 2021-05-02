package it.polimi.ingsw.client;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        //System.out.println("Insert server ip address:");
        //String ip = scanner.nextLine();
        String ip = "localhost";
        System.out.println("Insert server port number:");
        int port = scanner.nextInt();

        try {
            Socket socket = new Socket(ip, port);
            System.out.println(socket);

             Thread networkThread = new Thread(new NetworkHandler(socket));
             networkThread.start();
             networkThread.join();

        } catch (IOException | InterruptedException e) {
            System.out.println("Server unreachable");
        }
    }
}

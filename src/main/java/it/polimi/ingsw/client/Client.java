package it.polimi.ingsw.client;

import it.polimi.ingsw.server.ClientHandler;
import it.polimi.ingsw.server.messages.LoginRequestMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
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
            System.out.println(socket.toString());

             Thread networkThread = new Thread(new NetworkHandler(socket));
             networkThread.start();
             networkThread.join();

        } catch (IOException | InterruptedException e) {
            System.out.println("Server unreachable");
        }


    }


}

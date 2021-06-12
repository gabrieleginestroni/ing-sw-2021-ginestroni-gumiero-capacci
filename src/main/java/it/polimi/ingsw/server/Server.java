package it.polimi.ingsw.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

public class Server {
    public static final ConcurrentHashMap<String,Lobby> lobbies = new ConcurrentHashMap<>();
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Insert server port number:");
        int port = scanner.nextInt();
        System.out.println(port);
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(port);
            System.out.println(serverSocket);

        } catch (IOException e) {
            System.out.println("Server socket fail");
            System.exit(1);
            return;
        }

        while (true) {
            try {
                Socket clientSocket = serverSocket.accept();
                new Thread(new ClientHandler(clientSocket,lobbies)).start();
            } catch (IOException e) {
                System.out.println("connection dropped");
            }
        }
    }
}

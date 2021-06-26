package it.polimi.ingsw.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

public class Server {
    public static final ConcurrentHashMap<String,Lobby> lobbies = new ConcurrentHashMap<>();
    public static void main(String[] args) {
        int port = 50000; //default port
        for (int i = 0; i < args.length; i++){
            if (args[i].equals("-p")){
                i++;
                try{
                    port = Integer.parseInt(args[i]);
                }catch (Exception e){
                    System.out.println("Usage: server.jar [-p PORT_NUMBER]");
                    System.exit(-1);
                }
            }
        }


        if(port < 1 || port > 65535){
            System.out.println("Usage: server.jar [-p PORT_NUMBER]");
            System.exit(-1);
        }

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

package it.polimi.ingsw.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.SocketHandler;

public class ClientHandler implements Runnable {
    private final Socket clientSocket;
    private final ConcurrentHashMap<String,Lobby> lobbies;
    private  ObjectOutputStream output;
    private  ObjectInputStream input;

    public ClientHandler(Socket clientSocket, ConcurrentHashMap<String,Lobby> lobbies) {
        this.clientSocket = clientSocket;
        this.lobbies = lobbies;
        this.output = null;
        this.input = null;
    }

    @Override
    public void run() {
        System.out.println("Accepted client:" + " " + clientSocket.toString());
        try {
            output = new ObjectOutputStream(clientSocket.getOutputStream());
            input = new ObjectInputStream(clientSocket.getInputStream());

        } catch (IOException e) {
            System.out.println("could not open connection to " + clientSocket.getInetAddress());
            return;
        }

        try {
            Object loginMessage = input.readObject();

        } catch (Exception e){ System.out.println("invalid stream from client"); }


    }
}

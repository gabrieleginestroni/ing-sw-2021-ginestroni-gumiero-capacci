package it.polimi.ingsw.client.network_handler;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public abstract class NetworkHandler implements Runnable{
    final Socket socket;
    ObjectOutputStream output;
    ObjectInputStream input;

    NetworkHandler(Socket socket) {
        this.socket = socket;

        try {
            output = new ObjectOutputStream(socket.getOutputStream());
            input = new ObjectInputStream(socket.getInputStream());

        } catch (IOException e) {
            System.out.println("Could not open connection to " + socket.getInetAddress());
        }
    }
}

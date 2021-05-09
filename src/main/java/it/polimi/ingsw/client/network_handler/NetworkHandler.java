package it.polimi.ingsw.client.network_handler;

import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.server.messages.client_server.*;
import it.polimi.ingsw.server.messages.server_client.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class NetworkHandler implements Runnable {
    private final View view;
    final Socket socket;
    ObjectOutputStream output;
    ObjectInputStream input;

    public NetworkHandler(Socket socket, View view) {
        this.socket = socket;
        try {
            output = new ObjectOutputStream(socket.getOutputStream());
            input = new ObjectInputStream(socket.getInputStream());

        } catch (IOException e) {
            System.out.println("Could not open connection to " + socket.getInetAddress());
        }
        this.view = view;
    }

    @Override
    public void run() {
        try {
            AnswerMessage message;
            boolean gameOver = false;
            while(!gameOver){
                message = (AnswerMessage) input.readObject();
                message.selectView(view);
            }

            socket.close();
        } catch(IOException e) {
            view.showMessage("Server unreachable");
        }catch (ClassNotFoundException e) {
            view.showMessage("Invalid stream");
        }
    }

    public void sendMessage(Message message)  {
        try {
            output.writeObject(message);
        } catch (IOException e) {
            //TODO
        }
    }

}

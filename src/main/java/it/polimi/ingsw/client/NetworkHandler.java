package it.polimi.ingsw.client;

import it.polimi.ingsw.server.messages.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class NetworkHandler implements Runnable {
    private final Socket socket;
    private ObjectOutputStream output;
    private ObjectInputStream input;

    public NetworkHandler(Socket socket) {
        this.socket = socket;
        try {
            output = new ObjectOutputStream(socket.getOutputStream());
            input = new ObjectInputStream(socket.getInputStream());

        } catch (IOException e) {
            System.out.println("Could not open connection to " + socket.getInetAddress());

        }
    }

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Type nickname:");
        String nickname = scanner.nextLine();
        System.out.println("Type game ID:");
        String gameID = scanner.nextLine();

        try {
            output.writeObject(new LoginRequestMessage(gameID,nickname));
            AnswerMessage message;

            boolean loginStatus = true;
            while(loginStatus) {
                message = (AnswerMessage) input.readObject();
                message.selectView();

                if(message instanceof RequestLobbySizeMessage) {

                    output.writeObject(new LoginSizeMessage(scanner.nextInt()));
                }

                if(message instanceof LobbyFullMessage) {
                    loginStatus = false;
                    socket.close();
                }

                if(message instanceof LoginSuccessMessage) loginStatus = false;

                if(message instanceof LobbyNotReadyMessage) loginStatus = false;
            }

            socket.close();
        //boolean stop = false;
        //while (!stop) {}
        } catch(IOException e) { System.out.println("Server unreachable");}
         catch (ClassNotFoundException e) { System.out.println("Invalid stream");}




    }
}

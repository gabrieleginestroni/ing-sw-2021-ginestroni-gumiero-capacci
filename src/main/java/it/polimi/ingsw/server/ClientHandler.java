package it.polimi.ingsw.server;

import it.polimi.ingsw.server.messages.*;

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
    private Lobby gameLobby;
    private ObjectOutputStream output;
    private ObjectInputStream input;

    public ClientHandler(Socket clientSocket, ConcurrentHashMap<String,Lobby> lobbies) {
        this.clientSocket = clientSocket;
        this.lobbies = lobbies;
        this.gameLobby = null;
        try {
            output = new ObjectOutputStream(clientSocket.getOutputStream());
            input = new ObjectInputStream(clientSocket.getInputStream());

        } catch (IOException e) {
            System.out.println("Could not open connection to " + clientSocket.getInetAddress());

        }

    }

    @Override
    public void run() {
        System.out.println("Accepted client:" + " " + clientSocket.toString());


        try {
            Object message = input.readObject();
            if(message instanceof LoginRequestMessage) {
                LoginRequestMessage loginMessage = (LoginRequestMessage)message;

                String nickname = loginMessage.getNickname();
                String gameID = loginMessage.getRequestedGameID().toUpperCase();

                Lobby lobby = lobbies.get(gameID);
                if(lobby != null) {
                    if (lobby.isFull()){
                        if(lobby.getSize() != 0) sendAnswerMessage(new LobbyFullMessage());
                        else sendAnswerMessage(new LobbyNotReadyMessage());
                    }else {
                        lobby.addPlayer(nickname,this);
                        this.gameLobby = lobby;
                        sendAnswerMessage(new LoginSuccessMessage());
                    }
                } else {
                    lobby = new Lobby(gameID);
                    this.lobbies.put(gameID,lobby);

                    sendAnswerMessage(new RequestLobbySizeMessage());
                    message = input.readObject();
                    LoginSizeMessage sizeMessage = (LoginSizeMessage)message;

                    lobby.setSize(sizeMessage.getSize());
                    lobby.addPlayer(nickname,this);
                    this.gameLobby = lobby;
                    sendAnswerMessage(new LoginSuccessMessage());

                }

            }else{ System.out.println("Invalid message flow from client" + clientSocket.getInetAddress());}

        } catch (ClassNotFoundException e){ System.out.println("Invalid stream from client"); }
        catch(IOException e) {
            System.out.println("Client unreachable");
        }
        while(true){

        }


    }

    public void sendAnswerMessage(AnswerMessage message) throws IOException {
        output.writeObject(message);
    }
}
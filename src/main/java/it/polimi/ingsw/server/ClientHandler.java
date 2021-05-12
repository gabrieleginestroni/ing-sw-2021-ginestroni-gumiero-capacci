package it.polimi.ingsw.server;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.messages.client_server.LoginRequestMessage;
import it.polimi.ingsw.server.messages.client_server.LoginSizeMessage;
import it.polimi.ingsw.server.messages.client_server.Message;
import it.polimi.ingsw.server.messages.server_client.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

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
        try {
            loginPhase();
            while(!this.gameLobby.isGameStarted()){
                //sleep
            }
            gamePhase();

        } catch (ClassNotFoundException e){ System.out.println("Invalid stream from client"); }
        catch(IOException e) {
            System.out.println("Client unreachable");
        }



    }


    private void loginPhase() throws IOException, ClassNotFoundException {

        System.out.println("Accepted client:" + " " + clientSocket.toString());
        Message message;

        boolean loginStatus = true;
        while(loginStatus) {
            message = waitMessage();
            if (message instanceof LoginRequestMessage) {
                LoginRequestMessage loginMessage = (LoginRequestMessage) message;

                String nickname = loginMessage.getNickname();
                String gameID = loginMessage.getRequestedGameID().toUpperCase();

                Lobby lobby = lobbies.get(gameID);
                if (lobby != null) {
                    if (lobby.isFull()) {
                        if (lobby.getSize() != 0)
                            sendAnswerMessage(new LobbyFullMessage());
                        else
                            sendAnswerMessage(new LobbyNotReadyMessage());
                    } else {

                        if(lobby.isNicknameUsed(nickname)){
                            sendAnswerMessage(new NicknameAlreadyUsedMessage(gameID));
                        } else {
                            lobby.addPlayer(nickname, this);
                            this.gameLobby = lobby;
                            sendAnswerMessage(new LoginSuccessMessage(gameLobby.getPlayers()));
                            loginStatus = false;

                            if(lobby.isFull())
                                (new Thread(()->gameLobby.startGame())).start();
                            }
                    }
                } else {
                    lobby = new Lobby(gameID);
                    this.lobbies.put(gameID, lobby);

                    sendAnswerMessage(new RequestLobbySizeMessage());
                    message = waitMessage();
                    LoginSizeMessage sizeMessage = (LoginSizeMessage) message;

                    lobby.setSize(sizeMessage.getSize());
                    lobby.addPlayer(nickname, this);
                    this.gameLobby = lobby;
                    sendAnswerMessage(new LoginSuccessMessage(gameLobby.getPlayers()));
                    loginStatus = false;

                    if(lobby.isFull())
                        (new Thread(()->gameLobby.startGame())).start();
                }

            } else {
                System.out.println("Invalid message flow from client" + clientSocket.getInetAddress());
            }
        }
    }

    private void gamePhase() throws IOException, ClassNotFoundException {
        Controller controller= this.gameLobby.getController();

        while(!controller.isGameOver()){
            Message msg = waitMessage();
            controller.handleMessage(msg);
        }


    }

    public void sendAnswerMessage(AnswerMessage message) throws IOException {
        output.writeObject(message);
    }

    public Message waitMessage() throws IOException, ClassNotFoundException {
        Message msg = (Message)input.readObject();

        return msg;
    }
}

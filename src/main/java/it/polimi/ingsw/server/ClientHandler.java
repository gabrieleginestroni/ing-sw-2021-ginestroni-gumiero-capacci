package it.polimi.ingsw.server;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.Player;
import it.polimi.ingsw.server.messages.client_server.LoginRequestMessage;
import it.polimi.ingsw.server.messages.client_server.LoginSizeMessage;
import it.polimi.ingsw.server.messages.client_server.Message;
import it.polimi.ingsw.server.messages.client_server.Pong;
import it.polimi.ingsw.server.messages.server_client.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

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

        Thread pingThread = new Thread(()->{
            try {
                Controller controller= this.gameLobby.getController();
                while (!(controller.isGameOver() && controller.isRoundOver())) {
                    this.sendAnswerMessage(new Ping());
                    TimeUnit.SECONDS.sleep(5);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        try {
            loginPhase();

            while(!this.gameLobby.isGameStarted())
                TimeUnit.SECONDS.sleep(1);

            this.clientSocket.setSoTimeout(10000);
            pingThread.start();

            gamePhase();

        } catch(IOException e) {
            e.printStackTrace();
            pingThread.interrupt();
            if(gameLobby != null && lobbies.contains(gameLobby))
                gameLobby.notifyClientDisconnection(this);
            //System.out.println("Client stopped his execution");
        } catch (ClassNotFoundException e){
            System.out.println("Invalid stream from client");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            try {
                pingThread.interrupt();
                clientSocket.close();
                Thread.currentThread().interrupt();

            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
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
                    if(lobby.isPlayerDisconnected(nickname)){
                        lobby.reconnectClient(nickname, this);
                        this.gameLobby = lobby;
                        loginStatus = false;

                    }else {
                        if (lobby.isFull()) {
                            if (lobby.getSize() != 0)
                                sendAnswerMessage(new LobbyFullMessage());
                            else
                                sendAnswerMessage(new LobbyNotReadyMessage());
                        } else {
                            if(lobby.isGameStarted())
                                sendAnswerMessage(new LobbyFullMessage());
                            else{
                                if (lobby.isNicknameUsed(nickname))
                                    sendAnswerMessage(new NicknameAlreadyUsedMessage(gameID));
                                else {
                                    lobby.addPlayer(nickname, this);
                                    this.gameLobby = lobby;
                                    sendAnswerToAllPlayers(new LoginSuccessMessage(gameLobby.getPlayers()));
                                    loginStatus = false;

                                    if (lobby.isFull())
                                        (new Thread(() -> gameLobby.startGame())).start();
                                }
                            }
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
                    sendAnswerToAllPlayers(new LoginSuccessMessage(gameLobby.getPlayers()));
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

        while(!(controller.isGameOver() && controller.isRoundOver())){
            Message msg = waitMessage();
            controller.handleMessage(msg);

        }
    }

    public void sendAnswerToAllPlayers(AnswerMessage message){
        List<Player> players = gameLobby.getPlayers();

        players.stream().forEach(p ->  p.getClientHandler().sendAnswerMessage(message));
    }

    public void sendAnswerMessage(AnswerMessage message) {
        try {
            output.writeObject(message);
        } catch (IOException e) {
            System.out.println("PING SENT WHILE PLAYER DISCONNECTED");
            e.printStackTrace();
        }
    }

    public Message waitMessage() throws IOException, ClassNotFoundException {
        return (Message)input.readObject();
    }

    public Socket getClientSocket() {
        return clientSocket;
    }
}

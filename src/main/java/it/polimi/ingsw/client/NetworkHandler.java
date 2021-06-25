package it.polimi.ingsw.client;

import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.client.view.exceptions.invalidClientInputException;
import it.polimi.ingsw.server.messages.client_server.*;
import it.polimi.ingsw.server.messages.server_client.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

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

        Thread pingThread = new Thread(()->{
            try {
                while (!view.isGameOver()) {
                    this.sendMessage(new Pong());
                    TimeUnit.SECONDS.sleep(5);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        try {
            AnswerMessage message;
            boolean success;
            while(!view.isGameOver()){
                message = (AnswerMessage) input.readObject();

                 if(message instanceof GameStartedMessage || message instanceof ForcedReconnectionUpdateMessage) {
                    this.socket.setSoTimeout(10000);
                    pingThread.start();
                }

                success = false;
                while(!success){
                    try {
                        message.selectView(view);
                        success = true;

                    } catch (invalidClientInputException e) {
                        view.showMessage(e.getErrorMessage()+", please retry");
                    }
                    catch(Exception e){
                        //e.printStackTrace();
                        //consume the exception if it was thrown from the CLI
                        if(view.getScanner() != null && view.getScanner().hasNextLine())
                            view.getScanner().nextLine();
                        view.showMessage("Invalid input, please retry");
                    }
                }
            }

            socket.close();
            System.out.println("Match ended, press enter to exit");
            Scanner scanner = new Scanner(System.in);
            scanner.nextLine();
            System.exit(0);

        }  catch(SocketTimeoutException e) {
            view.showMessage("Connection lost");

        } catch(IOException e) {
            view.showMessage("Server stopped his execution");
        }catch (ClassNotFoundException e) {
            view.showMessage("Invalid stream");
        } finally {
            try {
                pingThread.interrupt();
                this.socket.setSoTimeout(0);
                this.socket.close();
                Thread.currentThread().interrupt();
            } catch (IOException ignored) {
            }
        }
    }

    public void sendMessage(Message message)  {
        try {
            output.writeObject(message);
        } catch (IOException ignored) {
        }
    }

}

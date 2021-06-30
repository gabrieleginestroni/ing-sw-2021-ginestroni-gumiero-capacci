package it.polimi.ingsw.client;

import it.polimi.ingsw.client.view.CLI;
import it.polimi.ingsw.server.messages.client_server.LoginRequestMessage;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

/**
 * @author Gabriele Ginestroni, Giacomo Gumiero, Tommaso Capacci
 * Class that launches the CLI version of the client, creates the NetworkHandler thread and handles the send
 * of the first LoginRequestMessage.
 */
public class ClientCLI {
    public static void main(String[] args) {
        int port = 50000;
        String ip ="localhost";
        for (int i = 0; i < args.length; i++){
            if (args[i].equals("-p")){
                i++;
                try{
                    port = Integer.parseInt(args[i]);
                }catch (Exception e){
                    System.out.println("Usage: cli.jar [-ip SERVER_IP] [-p PORT_NUMBER]");
                    System.exit(-1);
                }
            }
            else if (args[i].equals("-ip")){
                i++;
                try{
                    ip = args[i];
                }catch (Exception e){
                    System.out.println("Usage: cli.jar [-ip SERVER_IP] [-p PORT_NUMBER]");
                    System.exit(-1);
                }
            }
        }

        if(port < 1 || port > 65535){
            System.out.println("Usage: cli.jar [-ip SERVER_IP] [-p PORT_NUMBER]");
            System.exit(-1);
        }

        Scanner scanner = new Scanner(System.in);
        CLI view = new CLI();
        try {
            Socket socket = new Socket(ip, port);
            System.out.println(socket);
            NetworkHandler networkHandler = new NetworkHandler(socket, view);
            view.addNetworkHandler(networkHandler);

            view.showMessage("Type game ID:");
            String gameID = scanner.nextLine();
            String nickname;
            view.showMessage("Type nickname:");
            nickname = scanner.nextLine();
            while(nickname.length() > 20) {
                view.showMessage("Nickname must be < 20 chars");
                view.showMessage("Type nickname:");
                nickname = scanner.nextLine();
            }
            view.setNickname(nickname);
            networkHandler.sendMessage(new LoginRequestMessage(gameID,nickname));

            Thread networkThread = new Thread(networkHandler);
            networkThread.start();

            /*
            while(!view.isGameOver()){
                String input;
                scanner = new Scanner(System.in);
                TimeUnit.SECONDS.sleep(1);
                if(view.getCurrentPlayer() != null && !view.getCurrentPlayer().equals(view.getNickname())) {
                    input = scanner.nextLine();
                    if (!view.getCurrentPlayer().equals(view.getNickname()))
                        view.showMessage("It's " + view.getCurrentPlayer() + "'s turn, please wait yours.");
                    else{
                        String fakeInput = "fakeInput\n";
                        InputStream in = new ByteArrayInputStream(fakeInput.getBytes(StandardCharsets.UTF_8));
                        scanner = new Scanner(in);
                    }
                }
             }
             */

            networkThread.join();

        } catch (IOException | InterruptedException e) {
            System.out.println("Server unreachable");
        }
    }
}

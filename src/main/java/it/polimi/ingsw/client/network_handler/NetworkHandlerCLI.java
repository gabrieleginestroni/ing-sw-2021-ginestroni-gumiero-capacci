package it.polimi.ingsw.client.network_handler;

import it.polimi.ingsw.client.view.CLI;
import it.polimi.ingsw.server.messages.client_server.ChosenLeaderMessage;
import it.polimi.ingsw.server.messages.client_server.LoginRequestMessage;
import it.polimi.ingsw.server.messages.client_server.LoginSizeMessage;
import it.polimi.ingsw.server.messages.server_client.*;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class NetworkHandlerCLI extends NetworkHandler {
    private final CLI view;

    public NetworkHandlerCLI(Socket socket, CLI view) {
        super(socket);
        this.view = view;
    }

    @Override
    public void run() {

        try {
            loginPhase();
            leaderProposalPhase();
            gamePhase();


            socket.close();


        } catch(IOException e) { view.showMessage("Server unreachable");}
         catch (ClassNotFoundException e) { view.showMessage("Invalid stream");}
    }




    private void loginPhase() throws IOException, ClassNotFoundException {

        Scanner scanner = new Scanner(System.in);
        view.showMessage("Type nickname:");
        String nickname = scanner.nextLine();
        view.showMessage("Type game ID:");
        String gameID = scanner.nextLine();

        output.writeObject(new LoginRequestMessage(gameID,nickname));
        AnswerMessage message;

        boolean loginStatus = true;
        while(loginStatus) {
            message = (AnswerMessage) input.readObject();
            message.selectView(view);

            if(message instanceof RequestLobbySizeMessage) {

                output.writeObject(new LoginSizeMessage(scanner.nextInt()));
            }

            if(message instanceof LobbyFullMessage || message instanceof LobbyNotReadyMessage) {
                gameID = scanner.nextLine();

                output.writeObject(new LoginRequestMessage(gameID,nickname));
            }

            if(message instanceof LoginSuccessMessage)
                loginStatus = false;
        }

        boolean end = true;
        while(end) {
            message = (AnswerMessage) input.readObject();

            if(message instanceof MarketUpdateMessage) {
                System.out.println("market");
            }

            if(message instanceof BoardsUpdateMessage) {
                System.out.println("board");
                end = false;
            }

            if(message instanceof LorenzoUpdateMessage) {
                System.out.println("lorenzo");
            }

            if(message instanceof DevGridUpdateMessage) {
                System.out.println("grid");
            }

            message.selectView(view);
        }
    }

    private void leaderProposalPhase() throws IOException, ClassNotFoundException {
         boolean phaseCompleted = false;
        while(!phaseCompleted){
            Object message = input.readObject();
            int[] chosenLeaderCards = new int[2];
            Scanner sc = new Scanner(System.in);
            boolean success = false;

            if(message instanceof LeaderProposalMessage){
                while(!success){
                    ((LeaderProposalMessage) message).selectView(view);
                    int ind1 = sc.nextInt();
                    int ind2 = sc.nextInt();
                    if(0 <= ind1 && ind1 <= 3 && 0 <= ind2 && ind2 <= 3 && ind1 != ind2) {
                        chosenLeaderCards[0] = ind1;
                        chosenLeaderCards[1] = ind2;
                        success = true;
                    }
              }
              output.writeObject(new ChosenLeaderMessage(chosenLeaderCards));
            }

            if(message instanceof BoardsUpdateMessage) {
                System.out.println("board");
                ((BoardsUpdateMessage) message).selectView(view);
            }

            if(message instanceof InkwellMessage) {
                ((InkwellMessage)message).selectView(view);
                phaseCompleted = true;
            }
        }
    }

    private void gamePhase(){

    }


}

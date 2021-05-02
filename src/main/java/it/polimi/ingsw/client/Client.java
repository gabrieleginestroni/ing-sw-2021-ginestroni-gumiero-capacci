package it.polimi.ingsw.client;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Insert server ip address:");
        String ip = scanner.nextLine();
        System.out.println("Insert server port number:");
        int port = scanner.nextInt();
        try {
            Socket socket = new Socket(ip, port);
            System.out.println(socket.toString());
        } catch(Exception e){}



    }
}

package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static server.ServerRepository.clients;

public class Server {

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(8000)) {

            Thread streamer = new Thread(new Streamer());
            streamer.start();
            System.out.println("The server is lonely – send it some connection!");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Attention, cyberspace! A new client is in the house!");
                clients.add(clientSocket);
                Thread serverThread = new Thread(new ServerThread(clientSocket));
                serverThread.start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

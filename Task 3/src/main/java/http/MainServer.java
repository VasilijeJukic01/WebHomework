package http;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static config.Config.MAIN_SERVER_PORT;

public class MainServer {

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(MAIN_SERVER_PORT)) {
            while (true) {
                Socket socket = serverSocket.accept();
                new Thread(new MainServerThread(socket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

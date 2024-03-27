package helper;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static config.Config.HELPER_SERVER_PORT;

public class HelperServer {

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(HELPER_SERVER_PORT)) {
            while (true) {
                Socket socket = serverSocket.accept();
                new Thread(new HelperServerThread(socket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

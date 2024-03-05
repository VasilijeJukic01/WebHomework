package client;

import java.io.IOException;
import java.net.Socket;

public class Client {

    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 8000);

            Thread input = new Thread(new InputFlow(socket));
            Thread output = new Thread(new OutputFlow(socket));
            input.start();
            output.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

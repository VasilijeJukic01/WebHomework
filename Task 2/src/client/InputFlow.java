package client;

import util.Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class InputFlow implements Runnable {

    private final Socket socket;
    private BufferedReader reader;

    public InputFlow(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            while(true){
                String message = reader.readLine();
                System.out.println(message);
            }

        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            Utils.getInstance().closeResource(reader);
        }
    }

}

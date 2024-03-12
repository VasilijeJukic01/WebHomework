package client;

import util.Utils;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class OutputFlow implements Runnable {

    private final Socket socket;
    private PrintWriter writer;
    private Scanner reader;

    public OutputFlow(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()),true);
            reader = new Scanner(System.in);

            while(true) {
                String message = reader.nextLine().trim();
                writer.println(message);
            }

        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            Utils.getInstance().closeResource(writer);
            Utils.getInstance().closeResource(reader);
        }

    }

}

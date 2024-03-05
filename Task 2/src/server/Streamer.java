package server;

import encryptor.CryptoUtil;
import util.Utils;

import java.io.PrintWriter;
import java.util.Objects;

public class Streamer implements Runnable {

    private PrintWriter writer;

    @Override
    public void run() {
        try {
            while (true) {
                String message = ServerRepository.queue.take();
                String decryptedMessage = CryptoUtil.decrypt(message);
                notify(decryptedMessage);
            }
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        finally {
            Utils.closeResource(writer);
        }
    }

    public void notify(String message) {
        ServerRepository.clients.stream()
                .filter(Objects::nonNull)
                .filter(ServerRepository.users::containsValue)
                .forEach(clientSocket -> {
                    try {
                        writer = new PrintWriter(clientSocket.getOutputStream(), true);
                        writer.println(message);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }

}

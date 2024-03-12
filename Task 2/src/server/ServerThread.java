package server;

import encryptor.CryptoUtil;
import util.Utils;

import java.io.*;
import java.net.Socket;
import java.util.Random;

public class ServerThread implements Runnable {

    private final Socket socket;

    public ServerThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        BufferedReader reader = null;
        PrintWriter writer = null;

        try {
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);

            String user;

            // User check
            while (true) {
                writer.println("Enter a cool name: ");
                user = reader.readLine();

                if (!ServerRepository.users.containsKey(user.toLowerCase())) {
                    ServerRepository.users.put(user.toLowerCase(), socket);

                    PrintWriter w = writer;
                    ServerRepository.messages.forEach(message -> w.println(CryptoUtil.getInstance().decrypt(message)));

                    String encryptedMessage = CryptoUtil.getInstance().encrypt(ServerRepository.welcomes.get(
                            new Random().nextInt(ServerRepository.welcomes.size())).replace("%user", user));
                    ServerRepository.queue.add(encryptedMessage);

                    if (ServerRepository.messages.size() >= 100)
                        ServerRepository.messages.poll();

                    break;
                }
                else {
                    writer.println("Uh-oh! Looks like someone tried to pull a 'Username Déjà Vu.' ✨! Please pick a unique username and join the party!\"");
                }
            }

            // Chat
            while (true) {
                String message = reader.readLine();
                message = Utils.getInstance().format(message.trim(), user);

                String encryptedMessage = CryptoUtil.getInstance().encrypt(message);
                ServerRepository.queue.add(encryptedMessage);

                if (ServerRepository.messages.size() >= 100)
                    ServerRepository.messages.poll();

                ServerRepository.messages.offer(encryptedMessage);
            }

        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            Utils.getInstance().closeResource(reader);
            Utils.getInstance().closeResource(writer);
            Utils.getInstance().closeResource(socket);
        }
    }

}

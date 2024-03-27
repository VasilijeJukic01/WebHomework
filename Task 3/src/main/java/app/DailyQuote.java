package app;

import com.google.gson.Gson;

import java.io.*;
import java.net.Socket;

import static config.Config.HELPER_SERVER_PORT;

public class DailyQuote {

    private final Gson gson = new Gson();

    public Quote getDailyQuote() {
        try {
            Socket socket = new Socket("localhost", HELPER_SERVER_PORT);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);

            writer.println("GET / HTTP/1.1\r\nHost: localhost:"+HELPER_SERVER_PORT+"\r\n\r\n");

            reader.lines()
                    .takeWhile(line -> !line.trim().isEmpty())
                    .forEach(System.out::println);

            String quoteJson = reader.readLine();
            System.out.println(quoteJson);

            Quote quoteOfTheDay = gson.fromJson(quoteJson, Quote.class);

            socket.close();
            reader.close();
            writer.close();

            return quoteOfTheDay;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}

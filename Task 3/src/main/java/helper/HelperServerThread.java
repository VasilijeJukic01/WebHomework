package helper;

import com.google.gson.Gson;
import config.Repository;
import http.HttpMethod;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;
import java.util.StringTokenizer;

public class HelperServerThread implements Runnable {

    private final Socket socket;
    private final Gson gson = new Gson();

    public HelperServerThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);

            String requestLine = reader.readLine();

            StringTokenizer tokenizer = new StringTokenizer(requestLine);

            String method = tokenizer.nextToken();
            String path = tokenizer.nextToken();
            System.out.println(path);

            System.out.println("\nHTTP Request:\n");

            reader.lines()
                    .takeWhile(line -> !line.trim().isEmpty())
                    .forEach(System.out::println);

            Random rand = new Random();

            if(method.equals(HttpMethod.GET.toString()) && path.equals("/")) {
                StringBuilder quoteOfTheDay = new StringBuilder();
                quoteOfTheDay.append("HTTP/1.1 200 OK\r\nContent-Type: application/json\r\n\r\n");

                quoteOfTheDay.append(gson.toJson(Repository.getInstance().getDailyQuotes().get(rand.nextInt(Repository.getInstance().getDailyQuotes().size()))));

                System.out.println(quoteOfTheDay);
                writer.println(quoteOfTheDay);
            }
            else {
                writer.println("HTTP/1.1 404 Not Found\r\nContent-Type: text/html\r\n\r\n");
            }

            reader.close();
            writer.close();
            socket.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}

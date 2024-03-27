package http;

import app.Router;
import http.response.Response;

import java.io.*;
import java.net.Socket;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.concurrent.atomic.AtomicInteger;

public class MainServerThread implements Runnable {

    private final Socket client;

    public MainServerThread(Socket client) {
        this.client = client;
    }

    @Override
    public void run() {
        try{
            BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
            PrintWriter writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(client.getOutputStream())), true);

            HttpRequest httpRequest = readRequest(reader);
            Response response = handleRequest(httpRequest);
            sendResponse(writer, response);

            reader.close();
            writer.close();
            client.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private HttpRequest readRequest(BufferedReader reader) throws IOException {
        String requestLine = reader.readLine();

        StringTokenizer stringTokenizer = new StringTokenizer(requestLine);

        String method = stringTokenizer.nextToken();

        String path = stringTokenizer.nextToken();
        AtomicInteger contentLength = new AtomicInteger();

        System.out.println("\nHTTP Request:\n");
        System.out.println("HTTP Method: " + method + "\nPath: " + path);
        reader.lines()
                .takeWhile(line -> !line.trim().isEmpty())
                .forEach(line -> {
                    System.out.println(line);
                    if(line.contains("Content-Length")) {
                        contentLength.set(Integer.parseInt(line.split(": ")[1]));
                    }
                });

        Map<String, String> postParams = new HashMap<>();

        // Post request
        if(method.equals(HttpMethod.POST.toString())) {
            postParams = readPostParams(reader, contentLength.get());
            System.out.println("Post Parameters: " + postParams);
        }

        return new HttpRequest(HttpMethod.valueOf(method), path, postParams);
    }

    private Map<String, String> readPostParams(BufferedReader reader, int contentLength) throws IOException {
        char[] buff = new char[contentLength];
        reader.read(buff);
        String params = new String(buff);
        String[] split = params.split("&");

        Map<String, String> postParams = new HashMap<>();
        Arrays.stream(split)
                .map(s -> s.split("="))
                .forEach(kv -> postParams.put(kv[0], URLDecoder.decode(kv[1], StandardCharsets.UTF_8)));

        return postParams;
    }

    private Response handleRequest(HttpRequest httpRequest) throws Exception {
        Router router = new Router();
        return router.handle(httpRequest);
    }

    private void sendResponse(PrintWriter writer, Response response) {
        System.out.println("\nHTTP Response:\n");
        System.out.println(response.getResponseString());

        writer.println(response.getResponseString());
    }

}

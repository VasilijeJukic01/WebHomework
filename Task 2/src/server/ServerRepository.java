package server;

import java.net.Socket;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.LinkedBlockingDeque;

public final class ServerRepository {

    public static final Map<String, Socket> users = new ConcurrentHashMap<>();
    public static final List<Socket> clients = new CopyOnWriteArrayList<>();
    public static final BlockingQueue<String> messages = new LinkedBlockingDeque<>();
    public static final BlockingQueue<String> queue = new LinkedBlockingDeque<>();

    public static final Set<String> bannedWords = Set.of(
            "idiot", "crap", "damn", "heck"
    );

    public static final List<String> welcomes = List.of(
            "Watch out, folks! %user just rolled into the chat!",
            "Hold your keyboards, %user has joined the chat!",
            "Brace yourselves, %user is here!",
            "Alert the press, %user has landed in the chat!",
            "Whoa, %user just slid into the chat!",
            "Keep calm and welcome %user to the chat!",
            "Guess who's here? Yes, it's %user!",
            "Let's welcome %user, our newest chat superstar!",
            "Everyone, please give a warm welcome to %user!",
            "Look who just showed up! Welcome, %user!"
    );
}

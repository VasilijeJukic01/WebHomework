package util;

import server.ServerRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

public class Utils {

    private static volatile Utils instance = null;

    private Utils() {}

    public static Utils getInstance() {
        if (instance == null) {
            synchronized (Utils.class) {
                if (instance == null) {
                    instance = new Utils();
                }
            }
        }
        return instance;
    }

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

    public <T extends AutoCloseable> void closeResource(T resource) {
        Optional.ofNullable(resource).ifPresent(r -> {
            try {
                r.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public String format(String message, String user) {
        String[] words = message.split("(?<=[.,!?;:])|\\s");
        String formatted = Arrays.stream(words)
                .map(word -> {
                    String cleanedWord = word.replaceAll("\\W", "");
                    return ServerRepository.bannedWords.contains(cleanedWord) ? censor(word) : word;
                })
                .collect(Collectors.joining(" "))
                .replaceAll(" +", " ")
                .trim();

        return String.format("[%s] %s: %s", LocalDateTime.now().format(formatter), user, formatted);
    }

    private String censor(String word) {
        return word.charAt(0) +
                "*".repeat(Math.max(0, word.length() - 2)) +
                word.charAt(word.length() - 1);
    }

}

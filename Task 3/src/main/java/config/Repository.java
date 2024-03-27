package config;

import app.Quote;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Repository {

    private static volatile Repository instance;

    private final List<Quote> quotes = new CopyOnWriteArrayList<>();
    private final List<Quote> dailyQuotes = new ArrayList<>();

    private Repository() {
        init();
    }

    public static Repository getInstance() {
        if(instance == null){
            synchronized (Repository.class) {
                if(instance == null){
                    instance = new Repository();
                }
            }
        }
        return instance;
    }

    private void init() {
        dailyQuotes.add(new Quote("David Ogilvy", "The best ideas come as jokes. Make your thinking as funny as possible."));
        dailyQuotes.add(new Quote("Chuckleberry Finn", "I'm not lazy, I'm on energy-saving mode."));
        dailyQuotes.add(new Quote("Morning Mischief", "Why do we call it 'beauty sleep' when I wake up looking like a troll regardless?"));
        dailyQuotes.add(new Quote("Oscar Wilde", "The man who says his wife can't take a joke, forgets that she took him."));
        dailyQuotes.add(new Quote("Mike Bechtle", "People can't drive you crazy if you don't give them the keys."));
        dailyQuotes.add(new Quote("Leslie Nielsen",  "Truth hurts. Maybe not as much as jumping on a bicycle with a seat missing, but it hurts."));
        dailyQuotes.add(new Quote("Anonymous",  "Don't worry if it doesn’t work right. If everything did, you’d be out of a job."));
        dailyQuotes.add(new Quote("Vaskezinho",  "If you think programming is easy, just wait until you try naming variables."));
    }

    public List<Quote> getDailyQuotes() {
        return dailyQuotes;
    }

    public List<Quote> getQuotes() {
        return quotes;
    }

}

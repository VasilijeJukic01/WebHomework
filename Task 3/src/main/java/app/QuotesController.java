package app;

import config.Repository;
import http.HttpRequest;
import http.response.HtmlResponse;
import http.response.RedirectResponse;
import http.response.Response;

import java.util.stream.Collectors;

import static config.Config.HTML_TEMPLATE;

public class QuotesController extends Controller {

    public QuotesController(HttpRequest httpRequest) {
        super(httpRequest);
    }

    @Override
    public Response doGet() {
        Quote dailyQuote = new DailyQuote().getDailyQuote();

        String quotes = Repository.getInstance().getQuotes().stream()
                .map(q -> String.format("<tr><td><b>%s</b> - \"%s\"</td></tr>\n", q.getAuthor(), q.getText()))
                .collect(Collectors.joining());

        String body = String.format(HTML_TEMPLATE, dailyQuote.getAuthor(), dailyQuote.getText(), quotes);
        return new HtmlResponse(body);
    }

    @Override
    public Response doPost() {
        addQuoteFromRequest();
        return new RedirectResponse();
    }

    private void addQuoteFromRequest() {
        String author = httpRequest.getPostParams().get("author");
        String quote = httpRequest.getPostParams().get("quote");
        Repository.getInstance().getQuotes().add(new Quote(author, quote));
    }

}

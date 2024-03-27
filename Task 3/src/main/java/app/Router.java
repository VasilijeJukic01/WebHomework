package app;

import http.HttpMethod;
import http.HttpRequest;
import http.response.NoContentResponse;
import http.response.Response;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class Router {

    private final Map<RequestKey, Function<HttpRequest, Response>> handlers = new HashMap<>();

    public Router() {
        handlers.put(new RequestKey("/quotes",      HttpMethod.GET), this::handleQuotesGetRequest);
        handlers.put(new RequestKey("/save-quote",  HttpMethod.POST), this::handleSaveQuotePostRequest);
        handlers.put(new RequestKey("/favicon.ico", HttpMethod.GET), httpRequest -> new NoContentResponse());
    }

    public Response handle(HttpRequest httpRequest) throws Exception {
        Function<HttpRequest, Response> handler = handlers.get(new RequestKey(httpRequest.getPath(), httpRequest.getHttpMethod()));
        if (handler != null)
            return handler.apply(httpRequest);
        throw new Exception("Page: " + httpRequest.getPath() + ". Method: " + httpRequest.getHttpMethod() + " not found!");
    }

    // Response Handler
    private Response handleQuotesGetRequest(HttpRequest httpRequest) {
        return new QuotesController(httpRequest).doGet();
    }

    private Response handleSaveQuotePostRequest(HttpRequest httpRequest) {
        return new QuotesController(httpRequest).doPost();
    }

    // Request Record
    private record RequestKey(String path, HttpMethod method) {
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            RequestKey that = (RequestKey) o;
            return path.equals(that.path) && method == that.method;
        }
    }

}

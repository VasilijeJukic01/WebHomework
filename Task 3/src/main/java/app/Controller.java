package app;

import http.HttpRequest;
import http.response.Response;

public abstract class Controller {

    protected HttpRequest httpRequest;

    public Controller(HttpRequest httpRequest) {
        this.httpRequest = httpRequest;
    }

    public abstract Response doGet();

    public abstract Response doPost();
}

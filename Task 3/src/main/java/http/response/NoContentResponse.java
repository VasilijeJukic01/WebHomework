package http.response;

public class NoContentResponse implements Response {

    private final String html;

    public NoContentResponse() {
        this.html = "";
    }

    @Override
    public String getResponseString() {
        String response = "HTTP/1.1 204 No Content\r\nContent-Type: text/html\r\n\r\n";
        response += html;

        return response;
    }

}

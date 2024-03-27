package config;

public class Config {

    public static final int MAIN_SERVER_PORT = 8113;
    public static final int HELPER_SERVER_PORT = 8114;

    public static final String HTML_TEMPLATE =
            "<!DOCTYPE html>" +
                    "<html>" +
                    "<head>" +
                    "<meta charset=\"UTF-8\">" +
                    "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">" +
                    "<link rel=\"stylesheet\" href=\"https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css\">" +
                    "</head>" +
                    "<body class=\"p-3\">" +
                    "<form action=\"/save-quote\" method=\"POST\" class=\"mb-3\">" +
                    "<label for=\"author\">Author: </label><br>" +
                    "<input id=\"author\" name=\"author\" type=\"text\" class=\"form-control\" placeholder=\"Enter author\"><br>" +
                    "<label for=\"quote\">Quote: </label><br>" +
                    "<input id=\"quote\" name=\"quote\" type=\"text\" class=\"form-control\" placeholder=\"Enter quote\"><br><br>" +
                    "<button class=\"btn btn-primary\">Save Quote</button>" +
                    "</form>" +
                    "<br>" +
                    "<h2 class=\"mb-3\">Quote of the day:</h2>\n" +
                    "<p><b>%s</b> - <i>\"%s\"</i></p>\n" +
                    "<h2 class=\"mt-3 mb-3\">Saved quotes:</h2>" +
                    "<table class=\"table\">%s</table>" +
                    "<script src=\"https://code.jquery.com/jquery-3.3.1.slim.min.js\"></script>" +
                    "<script src=\"https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js\"></script>" +
                    "<script src=\"https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js\"></script>" +
                    "</body>" +
                    "</html>";


}

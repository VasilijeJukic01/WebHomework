package com.example.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class Repository {

    public static final List<String> days = List.of("MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY");

    public static final Map<String, List<String>> menu = new ConcurrentHashMap<>();

    static {
        days.forEach(day -> menu.put(day, new ArrayList<>()));
    }

    public static final String mainTemplate = "<!DOCTYPE html>\n" +
            "<html lang=\"en\">\n" +
            "<head>\n" +
            "    <meta charset=\"UTF-8\">\n" +
            "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
            "    <link href=\"https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css\" rel=\"stylesheet\">\n" +
            "</head>\n" +
            "<body>\n" +
            "    <h1 class=\"p-3\">Choose your food:</h1>\n" +
            "    <form action=\"submit\" method=\"post\" class=\"form-group p-3\">\n" +
            "        <label for=\"MONDAY\" class=\"d-block p-2\">Monday:</label>\n" +
            "        <select id=\"MONDAY\" name=\"MONDAY\" class=\"form-control mb-3\">%s</select>\n" +
            "        <label for=\"TUESDAY\" class=\"d-block p-2\">Tuesday:</label>\n" +
            "        <select id=\"TUESDAY\" name=\"TUESDAY\" class=\"form-control mb-3\">%s</select>\n" +
            "        <label for=\"WEDNESDAY\" class=\"d-block p-2\">Wednesday:</label>\n" +
            "        <select id=\"WEDNESDAY\" name=\"WEDNESDAY\" class=\"form-control mb-3\">%s</select>\n" +
            "        <label for=\"THURSDAY\" class=\"d-block p-2\">Thursday:</label>\n" +
            "        <select id=\"THURSDAY\" name=\"THURSDAY\" class=\"form-control mb-3\">%s</select>\n" +
            "        <label for=\"FRIDAY\" class=\"d-block p-2\">Friday:</label>\n" +
            "        <select id=\"FRIDAY\" name=\"FRIDAY\" class=\"form-control mb-3\">%s</select>\n" +
            "        <br/><button type=\"submit\" class=\"btn btn-primary\">Submit</button>\n" +
            "    </form>\n" +
            "    <script src=\"https://code.jquery.com/jquery-3.3.1.slim.min.js\"></script>\n" +
            "    <script src=\"https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js\"></script>\n" +
            "    <script src=\"https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js\"></script>\n" +
            "</body>\n" +
            "</html>";

    public static final String submitTemplate = "<!DOCTYPE html>\n" +
            "<html lang=\"en\">\n" +
            "<head>\n" +
            "    <meta charset=\"UTF-8\">\n" +
            "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
            "    <link href=\"https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css\" rel=\"stylesheet\">\n" +
            "    <style>\n" +
            "        body {\n" +
            "            padding: 20px;\n" +
            "        }\n" +
            "        .list-group-item {\n" +
            "            margin-bottom: 10px;\n" +
            "        }\n" +
            "    </style>\n" +
            "</head>\n" +
            "<body>\n" +
            "    <h1>You order has been placed!</h1>\n" +
            "    <ul class=\"list-group\">\n" +
            "        <li class=\"list-group-item\">Monday - %s</li>\n" +
            "        <li class=\"list-group-item\">Tuesday - %s</li>\n" +
            "        <li class=\"list-group-item\">Wednesday - %s</li>\n" +
            "        <li class=\"list-group-item\">Thursday - %s</li>\n" +
            "        <li class=\"list-group-item\">Friday - %s</li>\n" +
            "    </ul>\n" +
            "    <form method=\"get\" action=\"/order\" class=\"mt-3\">\n" +
            "        <input type=\"hidden\" name=\"password\" value=\"123456\">\n" +
            "        <button type=\"submit\" class=\"btn btn-primary\">Chosen food</button>\n" +
            "    </form>\n" +
            "    <script src=\"https://code.jquery.com/jquery-3.3.1.slim.min.js\"></script>\n" +
            "    <script src=\"https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js\"></script>\n" +
            "    <script src=\"https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js\"></script>\n" +
            "</body>\n" +
            "</html>";

    public static final String alreadySubmittedTemplate = "<!DOCTYPE html>\n" +
            "<html lang=\"en\">\n" +
            "<head>\n" +
            "    <meta charset=\"UTF-8\">\n" +
            "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
            "    <link href=\"https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css\" rel=\"stylesheet\">\n" +
            "</head>\n" +
            "<body>\n" +
            "    <h1>You have already been submitted your order.</h1>\n" +
            "    <ul class=\"list-group\">\n" +
            "        <li class=\"list-group-item\"><strong>Monday</strong> - %s</li>\n" +
            "        <li class=\"list-group-item\"><strong>Tuesday</strong> - %s</li>\n" +
            "        <li class=\"list-group-item\"><strong>Wednesday</strong> - %s</li>\n" +
            "        <li class=\"list-group-item\"><strong>Thursday</strong> - %s</li>\n" +
            "        <li class=\"list-group-item\"><strong>Friday</strong> - %s</li>\n" +
            "    </ul>\n" +
            "    <form method=\"get\" action=\"/order\" class=\"mt-3\">\n" +
            "        <input type=\"hidden\" name=\"password\" value=\"123456\">\n" +
            "        <button type=\"submit\" class=\"btn btn-primary\">Chosen food</button>\n" +
            "    </form>\n" +
            "    <script src=\"https://code.jquery.com/jquery-3.3.1.slim.min.js\"></script>\n" +
            "    <script src=\"https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js\"></script>\n" +
            "    <script src=\"https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js\"></script>\n" +
            "</body>\n" +
            "</html>";

    public static final String orderTemplate = "<!DOCTYPE html>\n" +
            "<html lang=\"en\">\n" +
            "<head>\n" +
            "    <meta charset=\"UTF-8\">\n" +
            "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
            "    <link href=\"https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css\" rel=\"stylesheet\">\n" +
            "</head>\n" +
            "<body class=\"p-3\">\n" +
            "    <h1>Chosen food</h1>\n" +
            "    <form method=\"post\" action=\"/order?password=%s\" class=\"mb-3\">\n" +
            "        <button type=\"submit\" class=\"btn btn-primary\">Clear</button>\n" +
            "    </form>\n" +
            "</body>\n" +
            "</html>";

    public static final String dayMenuTemplate = "<h1 class=\"mb-3\">%s</h1>";

    public static final String tableTemplate = "<table class=\"table mb-3\">" +
            "<thead>" +
            "<tr>" +
            "<th scope=\"col\">Food</th>" +
            "<th scope=\"col\" class=\"text-center\">Amount</th>" +
            "</tr>" +
            "</thead>" +
            "<tbody>%s</tbody>" +
            "</table>";

    public static final String unauthorizedTemplate = "<!DOCTYPE html>\n" +
            "<html lang=\"en\">\n" +
            "<head>\n" +
            "    <meta charset=\"UTF-8\">\n" +
            "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
            "    <link href=\"https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css\" rel=\"stylesheet\">\n" +
            "</head>\n" +
            "<body class=\"p-3\">\n" +
            "    <h1>Unauthorized</h1>\n" +
            "</body>\n" +
            "</html>";

}

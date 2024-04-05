package com.example.servlets;

import com.example.repository.Repository;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@WebServlet(name = "orderServlet", value = "/order")
public class OrderServlet extends HttpServlet {

    private String password = "";

    public void init() {
        try {
            URL resourceUrl = getClass().getResource("/password.txt");
            if (resourceUrl == null) {
                System.out.println("Resource not found: /password.txt");
                return;
            }

            Path path = Paths.get(resourceUrl.toURI());

            try (Stream<String> lines = Files.lines(path)) {
                password = lines.findFirst().orElse("");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter writer = response.getWriter();

        Map<String, Map<String, Integer>> menu = (Map<String, Map<String, Integer>>) super.getServletContext().getAttribute("userOrder");

        if(request.getParameter("password") != null && request.getParameter("password").equals(password)) {
            writer.println(String.format(Repository.orderTemplate, password));

            Repository.days.forEach(day -> generateDayMenu(writer, menu, day));

            writer.println("<form method=\"get\" action=\"/food" + "\">");
            writer.println("<button type=\"submit\">Chose food</button>");
            writer.println("</form>");
        }
        else {
            response.setStatus(403);
            writer.println(Repository.unauthorizedTemplate);
        }
    }

    private void generateDayMenu(PrintWriter writer, Map<String, Map<String, Integer>> menu, String day) {
        writer.println(String.format(Repository.dayMenuTemplate, day));
        createTable(writer, menu.get(day));
    }

    private void createTable(PrintWriter writer, Map<String, Integer> dayMenu) {
        String tableRows = dayMenu.entrySet().stream()
                .map(entry -> generateTableRow(entry.getKey(), entry.getValue().toString()))
                .collect(Collectors.joining());

        writer.println(String.format(Repository.tableTemplate, tableRows));
    }

    public String generateTableRow(String food, String amount) {
        return String.format("<tr><td>%s</td><td class=\"text-center\" style=\"width: 100px;\">%s</td></tr>", food, amount);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, Map<String, Integer>> menu = (Map<String, Map<String, Integer>>) super.getServletContext().getAttribute("userOrder");
        List<String> users = (CopyOnWriteArrayList<String>) super.getServletContext().getAttribute("submittedUsers");

        Repository.days.forEach(day -> menu.get(day).clear());

        users.clear();
        response.sendRedirect("/food");
    }

}

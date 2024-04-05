package com.example.servlets;

import com.example.repository.Repository;

import javax.servlet.http.*;
import javax.servlet.annotation.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@WebServlet(name = "mainServlet", value = "/food")
public class MainServlet extends HttpServlet {

    public void init() {
        initFood();
        initContext();

        System.out.println("Initialization complete.");
        Repository.menu.forEach((day, food) -> System.out.println(day + ": " + food));
    }

    private void initFood() {
        ClassLoader classLoader = getClass().getClassLoader();
        Repository.days.stream()
                .map(name -> classLoader.getResource(name.toLowerCase() + ".txt"))
                .filter(Objects::nonNull)
                .map(URL::getFile)
                .map(File::new)
                .filter(File::exists)
                .forEach(file -> {
                    try (Scanner scanner = new Scanner(file)) {
                        String day = file.getName().replace(".txt", "").toUpperCase();
                        while (scanner.hasNextLine()) {
                            String line = scanner.nextLine();
                            Repository.menu.get(day).add(line);
                        }
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    private void initContext() {
        Map<String, Map<String, Integer>> order = Repository.days.stream()
                .collect(Collectors.toConcurrentMap(day -> day, day -> new ConcurrentHashMap<>()));

        super.getServletContext().setAttribute("submittedUsers", new CopyOnWriteArrayList<>());
        super.getServletContext().setAttribute("userOrder", order);
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter writer = response.getWriter();

        String[] options = new String[Repository.days.size()];
        String[] orders = new String[Repository.days.size()];

        IntStream.range(0, Repository.days.size()).forEach(i -> {
            options[i] = createOptionsForDay(Repository.days.get(i));
            orders[i] = (String) request.getSession().getAttribute(Repository.days.get(i));
        });

        @SuppressWarnings("unchecked")
        List<String> submittedUsers = (CopyOnWriteArrayList<String>) super.getServletContext().getAttribute("submittedUsers");
        if (!submittedUsers.contains(request.getSession().getId())) {
            String formattedHtml = String.format(Repository.mainTemplate, (Object[]) options);
            writer.println(formattedHtml);
        }
        else {
            String formattedUserOrder = String.format(Repository.alreadySubmittedTemplate, (Object[]) orders);
            writer.println(formattedUserOrder);
        }
        writer.println("</body></html>");
    }

    private String createOptionsForDay(String day) {
        return Repository.menu.get(day).stream()
                .map(food -> "<option value=\"" + food + "\">" + food + "</option>")
                .collect(Collectors.joining());
    }

}

package com.example.servlets;

import com.example.repository.Repository;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

@WebServlet(name = "submitServlet", value = "/submit")
public class SubmitServlet extends HttpServlet {

    @Override
    @SuppressWarnings("unchecked")
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        List<String> users = (CopyOnWriteArrayList<String>) super.getServletContext().getAttribute("submittedUsers");
        Map<String, Map<String, Integer>> menu = (Map<String, Map<String, Integer>>) super.getServletContext().getAttribute("userOrder");

        users.add(request.getSession().getId());

        Repository.days.forEach(day -> {
            updateMenuForDay(day, day, menu, request);
            request.getSession().setAttribute(day, request.getParameter(day));
        });

        PrintWriter writer = response.getWriter();
        String formattedHtml = String.format(Repository.submitTemplate,
                request.getParameter("MONDAY"),
                request.getParameter("TUESDAY"),
                request.getParameter("WEDNESDAY"),
                request.getParameter("THURSDAY"),
                request.getParameter("FRIDAY")
        );
        writer.println(formattedHtml);
    }

    private void updateMenuForDay(String day, String parameter, Map<String, Map<String, Integer>> menu, HttpServletRequest request) {
        // If menu does not contain food for the day
        menu.get(day).putIfAbsent(request.getParameter(parameter), 0);
        // Increment the count of the food for the day
        menu.get(day).put(request.getParameter(parameter), menu.get(day).get(request.getParameter(parameter)) + 1);
    }

}

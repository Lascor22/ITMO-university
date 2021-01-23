package ru.itmo.wp.servlet;

import com.google.gson.Gson;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Objects;


public class MessageServlet extends HttpServlet {
    private ArrayList<Message> messages = new ArrayList<>();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String requestBody = getBody(request);
        PrintWriter printWriter = new PrintWriter(response.getOutputStream(), true, StandardCharsets.UTF_8);

        if (request.getRequestURI().endsWith("add")) {
            String text = "";
            if (requestBody.contains("=")) {
                if ("text".equals(requestBody.split("=")[0])) {
                    text = requestBody.split("=")[1];
                }
                messages.add(new Message(String.valueOf(request.getSession().getAttribute("user")), text));
            }
        } else if (request.getRequestURI().endsWith("auth")) {
            String userName = "";
            if (requestBody.contains("=")) {
                userName = requestBody.split("=")[1];
            }
            HttpSession session = request.getSession();
            session.setAttribute("user", userName);
            String json = new Gson().toJson(userName);
            printWriter.print(json);
            response.setContentType("application/json");
        } else if (request.getRequestURI().endsWith("findAll")) {

            String json = new Gson().toJson(messages);
            printWriter.print(json);
            response.setContentType("application/json");
        }
        printWriter.close();
    }

    private String getBody(HttpServletRequest request) throws IOException {
        BufferedReader reader = request.getReader();
        StringBuilder requestBody = new StringBuilder();
        while (reader.ready()) {
            requestBody.append(java.net.URLDecoder.decode(reader.readLine(), StandardCharsets.UTF_8.name()));
        }
        reader.close();
        return requestBody.toString();
    }

    private static class Message {
        private String user;
        private String text;

        public String getUser() {
            return user;
        }

        public void setUser(String user) {
            this.user = user;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Message message = (Message) o;
            return Objects.equals(user, message.user) &&
                    Objects.equals(text, message.text);
        }

        @Override
        public int hashCode() {
            return Objects.hash(user, text);
        }

        Message(String user, String text) {
            this.user = user;
            this.text = text;
        }
    }
}
package ru.itmo.wp.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;

public class StaticServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String sourceUri = request.getRequestURI();
        OutputStream outputStream = response.getOutputStream();
        String[] arrayOfUri = sourceUri.split("\\+");
        for (String uri : arrayOfUri) {
            File file = checkHotFiles(uri);
            if (file.isFile()) {
                if (uri.equals(arrayOfUri[0])) {
                    response.setContentType(getContentTypeFromName(uri));
                }
                Files.copy(file.toPath(), outputStream);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        }
        outputStream.flush();
    }

    private File checkHotFiles(String uri) {
        File file = new File(new File(System.getenv("STATIC_WEB")), uri);
        if (!file.isFile()) {
            file = new File(getServletContext().getRealPath("/static" + uri));
        }
        return file;
    }

    private String getContentTypeFromName(String name) {
        name = name.toLowerCase();

        if (name.endsWith(".png")) {
            return "image/png";
        }

        if (name.endsWith(".jpg")) {
            return "image/jpeg";
        }

        if (name.endsWith(".html")) {
            return "text/html";
        }

        if (name.endsWith(".css")) {
            return "text/css";
        }

        if (name.endsWith(".js")) {
            return "application/javascript";
        }

        throw new IllegalArgumentException("Can't find content type for '" + name + "'.");
    }
}

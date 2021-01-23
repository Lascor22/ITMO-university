package ru.itmo.wp.servlet;

import ru.itmo.wp.util.ImageUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.Base64;

public class CaptchaFilter extends HttpFilter {
    private final File dynamicCaptcha = new File("D:\\Projects\\webW3T1\\src\\main\\webapp\\services\\captcha.html");

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        PrintWriter printWriter = new PrintWriter(response.getOutputStream(), true, StandardCharsets.UTF_8);

        if ("GET".equals(request.getMethod()) && request.getSession().getAttribute("captcha") == null) {
            if (request.getSession().getAttribute("captcha-number") == null) {
                responseCaptcha(request, response, printWriter);
            } else {
                if (request.getParameter("answer").equals(request.getSession().getAttribute("captcha-number").toString())) {
                    request.getSession().setAttribute("captcha", true);
                    System.out.println(getBody(request));
                    System.out.println(getBody(request));
                    chain.doFilter(request, response);
                } else {
                    responseCaptcha(request, response, printWriter);
                }
            }
        } else {
            chain.doFilter(request, response);
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

    private void responseCaptcha(HttpServletRequest request, HttpServletResponse response, PrintWriter printWriter) throws IOException {
        final Random randomGenerator = new Random();
        final int expectedNumber = 100 + randomGenerator.nextInt(899);

        request.getSession().setAttribute("captcha-number", Integer.toString(expectedNumber));
        byte[] captcha = ImageUtils.toPng(Integer.toString(expectedNumber));

        String html = getHtml(dynamicCaptcha).replaceAll("CAPTCHA",
                new String(Base64.getEncoder().encode(captcha))).replaceAll("GET_URL", request.getRequestURI());
        printWriter.print(html);
        response.setContentType("text/html");
    }

    private String getHtml(File htmlFile) throws IOException {
        BufferedReader readHtml = new BufferedReader(new InputStreamReader(new FileInputStream(htmlFile)));
        StringBuilder htmlBody = new StringBuilder();
        while (readHtml.ready()) {
            htmlBody.append(readHtml.readLine());
        }
        readHtml.close();
        return htmlBody.toString();

    }
}

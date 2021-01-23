package ru.itmo.wp.web.domain;

import com.google.common.base.Strings;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public abstract class Page {
    private final UserService userService = new UserService();
    private HttpServletRequest request;
    private Map<String, Object> view;

    public void action() {}

    public void action(HttpServletRequest request) {}

    public void action(HttpServletRequest request, Map<String, Object> view) {}

    protected void before(HttpServletRequest request, Map<String, Object> view) {
        if (this.request == null) {
            this.request = request;
        }
        if (this.view == null) {
            this.view = view;
        }

        view.put("userCount", userService.findCount());
        putUser();
        putMessage();
    }

    protected void after(HttpServletRequest request, Map<String, Object> view) {
    }

    protected void setMessage(String message) {
        request.getSession().setAttribute("message", message);
    }

    private void putUser() {
        User user = (User) request.getSession().getAttribute("user");
        if (user != null) {
            view.put("user", user);
        }
    }

    protected void putAllUsers() {
        view.put("users", userService.findAll());
    }

    private void putMessage() {
        String message = (String) request.getSession().getAttribute("message");
        if (!Strings.isNullOrEmpty(message)) {
            view.put("message", message);
            request.getSession().removeAttribute("message");
        }
    }

    protected void setUser(User user) {
        request.getSession().setAttribute("user", user);
    }

    protected User getUser() {
        return (User) request.getSession().getAttribute("user");
    }

}

package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.service.UserService;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @noinspection unused
 */
public class UsersPage {
    private final UserService userService = new UserService();

    private void action(HttpServletRequest request, Map<String, Object> view) {
    }

    private void findAll(HttpServletRequest request, Map<String, Object> view) {
        view.put("users", userService.findAll());
    }

    private void changeAdminStatus(HttpServletRequest request, Map<String, Object> view) {
        User user = (User) request.getSession().getAttribute("user");
        if (user != null && user.isAdmin()) {
            long id = Long.parseLong(request.getParameter("id"));
            boolean value = Boolean.parseBoolean(request.getParameter("value"));
            if (id == user.getId()) {
                view.put("error", "You cannot change yourself");
                view.put("answer", false);
            } else {
                userService.changeAdminStatus(id, value);
                view.put("answer", true);
            }
        } else {
            request.getSession().setAttribute("message", "you are not admin!");
            throw new RedirectException("/index");
        }
    }

    private void findUser(HttpServletRequest request, Map<String, Object> view) {
        view.put("user",
                userService.find(Long.parseLong(request.getParameter("userId"))));
    }
}

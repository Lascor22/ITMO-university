package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.service.ArticleService;
import ru.itmo.wp.model.service.UserService;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @noinspection unused
 */

public class MyArticlesPage {
    private final ArticleService articleService = new ArticleService();
    private final UserService userService = new UserService();

    private void action(HttpServletRequest request, Map<String, Object> view) {
        if (request.getSession().getAttribute("user") == null) {
            request.getSession().setAttribute("message", "Enter please");
            throw new RedirectException("/index");
        } else {
            view.put("articles", articleService.findAllByUserId(((User) request.getSession().getAttribute("user")).getId()));
        }
    }

    private void changeHide(HttpServletRequest request, Map<String, Object> view) {
        long id = Long.parseLong(request.getParameter("id"));
        boolean value = Boolean.parseBoolean(request.getParameter("value"));
        User user = (User) request.getSession().getAttribute("user");
        if (user != null && articleService.find(id).getUserId() == user.getId()) {
            articleService.changeHidden(id, value);
        }
    }
}

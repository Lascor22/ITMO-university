package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.Article;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.service.ArticleService;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;



/**
 * @noinspection unused
 */
public class ArticlePage {
    private final ArticleService articleService = new ArticleService();

    private void action(HttpServletRequest request, Map<String, Object> view) {
        if (request.getSession().getAttribute("user") == null) {
            request.getSession().setAttribute("message", "Enter please");
            throw new RedirectException("/index");
        }
    }

    private void create(HttpServletRequest request, Map<String, Object> view) throws ValidationException {
        Article article = new Article();
        article.setTitle(request.getParameter("title"));
        article.setText(request.getParameter("text"));
        article.setHidden("true".equalsIgnoreCase(request.getParameter("hidden")));
        article.setUserId(((User) request.getSession().getAttribute("user")).getId());
        articleService.validateArticle(article);
        articleService.addNewArticle(article);
        request.getSession().setAttribute("message", "new article created!");
        throw new RedirectException("/index");

    }
}

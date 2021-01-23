package ru.itmo.wp.model.service;

import ru.itmo.wp.model.domain.Article;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.repository.ArticleRepository;
import ru.itmo.wp.model.repository.impl.ArticleRepositoryImpl;

import java.util.List;

public class ArticleService {
    private final ArticleRepository articleRepository = new ArticleRepositoryImpl();

    public void addNewArticle(Article article) {
        articleRepository.save(article);
    }

    public List<Article> findAllByUserId(long userId) {
        return articleRepository.findByUserId(userId);
    }

    public void validateArticle(Article article) throws ValidationException {
        if (article.getTitle().length() > 255) {
            throw new ValidationException("title longer than 255");
        }
        if (article.getText().isEmpty()) {
            throw new ValidationException("text is empty");
        }
        if (article.getTitle().isEmpty()) {
            throw new ValidationException("title is empty");
        }
    }

    public List<Article> findAllUnhidden() {
        return articleRepository.findAllUnhidden();
    }

    public Article find(long id) {
        return articleRepository.find(id);
    }

    public void changeHidden(long id, boolean value) {
        articleRepository.changeHidden(id, value);
    }

}

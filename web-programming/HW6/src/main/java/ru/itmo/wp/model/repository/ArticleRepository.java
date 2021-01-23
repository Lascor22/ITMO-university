package ru.itmo.wp.model.repository;

import ru.itmo.wp.model.domain.Article;

import java.util.List;

public interface ArticleRepository {
    Article find(long id);

    List<Article> findByUserId(long userId);

    List<Article> findAll();

    List<Article> findAllUnhidden();

    void changeHidden(long id, boolean value);

    void save(Article article);
}

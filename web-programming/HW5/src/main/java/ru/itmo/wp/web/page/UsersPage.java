package ru.itmo.wp.web.page;

import ru.itmo.wp.web.domain.Page;
import ru.itmo.wp.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @noinspection unused
 */
public class
UsersPage extends Page {
    @Override
    public void action() {
        putAllUsers();
    }

}

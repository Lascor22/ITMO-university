package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.Event;
import ru.itmo.wp.model.domain.EventTypes;
import ru.itmo.wp.web.domain.Page;
import ru.itmo.wp.model.service.EventService;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @noinspection unused
 */
public class LogoutPage extends Page {
    private final EventService eventService = new EventService();

    @Override
    public void action(HttpServletRequest request) {
        Event event = new Event();
        event.setUserId(getUser().getId());
        event.setType(EventTypes.LOGOUT);
        eventService.addEvent(event);
        request.getSession().removeAttribute("user");

        setMessage("Good bye. Hope to see you soon!");
        throw new RedirectException("/index");
    }

}

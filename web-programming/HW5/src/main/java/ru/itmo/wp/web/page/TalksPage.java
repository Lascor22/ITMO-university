package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.Talk;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.service.TalkService;
import ru.itmo.wp.model.service.UserService;
import ru.itmo.wp.web.domain.Page;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class TalksPage extends Page {
    private final TalkService talkService = new TalkService();

    @Override
    public void action(HttpServletRequest request, Map<String, Object> view) {
        if (getUser() != null) {
            view.put("talks", talkService.findAll(getUser()));
            putAllUsers();
        } else {
            setMessage("Enter please");
            throw new RedirectException("/enter");
        }
    }

    private void sendMessage(HttpServletRequest request) throws ValidationException {
        if (getUser() != null) {
            Talk talk = new Talk();
            talk.setSourceUserId(getUser().getId());
            talk.setText(request.getParameter("text"));
            talkService.validateTalk(talk, request.getParameter("targetLogin"));
            talkService.saveTalk(talk);
            setMessage("You are successfully send message!");
            throw new RedirectException("/talks");
        } else {
            setMessage("Enter please");
            throw new RedirectException("/enter");
        }
    }

}

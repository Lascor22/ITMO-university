package ru.itmo.wp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itmo.wp.form.NoticeCredentials;
import ru.itmo.wp.service.NoticeService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class NoticePage extends Page {
    private final NoticeService noticeService;

    public NoticePage(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @GetMapping("notice")
    public String notice(HttpSession httpSession, Model model) {
        if (getUser(httpSession) != null) {
            model.addAttribute("noticeArea", new NoticeCredentials());
            return "NoticePage";
        }
        putMessage(httpSession, "log in please");
        return "redirect:/";
    }

    @PostMapping("notice")
    public String create(@Valid @ModelAttribute("noticeArea") NoticeCredentials noticeArea, BindingResult bindingResult, HttpSession httpSession) {
        if (bindingResult.hasErrors()) {
            return "NoticePage";
        }
        noticeService.create(noticeArea.getContent());
        putMessage(httpSession, "Notice created");
        return "redirect:/";
    }
}

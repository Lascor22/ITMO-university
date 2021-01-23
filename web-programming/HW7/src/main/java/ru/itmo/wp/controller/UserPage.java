package ru.itmo.wp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.itmo.wp.domain.User;
import ru.itmo.wp.service.UserService;

import javax.servlet.http.HttpSession;

@Controller
public class UserPage extends Page {
    private final UserService userService;

    public UserPage(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("user/{id}")
    public String user(@PathVariable("id") String id, Model model) {
        User user = null;
        try {
            user = userService.findById(Long.parseLong(id));
        } catch (NumberFormatException e) {
            // no Operation
        }
        model.addAttribute("user", user);
        return "UserPage";
    }

    @GetMapping("user")
    public String emptyUser(HttpSession httpSession) {
        putMessage(httpSession, "please vote user");
        return "redirect:/users/all";
    }
}

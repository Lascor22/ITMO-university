package ru.itmo.wp.controller;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.itmo.wp.domain.User;
import ru.itmo.wp.exception.ValidationException;
import ru.itmo.wp.form.RegisterUserCredentials;
import ru.itmo.wp.service.UserService;
import ru.itmo.wp.util.BindingResultUtils;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/1")
public class UserController extends ApiController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("users/authorized")
    public User findAuthorized(User user) {
        return user;
    }

    @GetMapping("users")
    public List<User> findAll() {
        return userService.findAll();
    }

    @PostMapping("users")
    public void register(@RequestBody @Valid RegisterUserCredentials registerUserCredentials, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException(BindingResultUtils.getErrorMessage(bindingResult));
        }
        userService.save(registerUserCredentials);
    }
}

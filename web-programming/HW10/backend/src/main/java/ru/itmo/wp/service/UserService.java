package ru.itmo.wp.service;

import org.springframework.stereotype.Service;
import ru.itmo.wp.domain.User;
import ru.itmo.wp.form.RegisterUserCredentials;
import ru.itmo.wp.repository.UserRepository;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findByLoginAndPassword(String login, String password) {
        return login == null || password == null ? null : userRepository.findByLoginAndPassword(login, password);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public void save(RegisterUserCredentials registerUserCredentials) {
        User user = new User();
        user.setLogin(registerUserCredentials.getLogin());
        user.setName(registerUserCredentials.getName());
        userRepository.save(user);
        userRepository.updatePasswordSha(user.getId(), user.getLogin(), registerUserCredentials.getPassword());
    }
}

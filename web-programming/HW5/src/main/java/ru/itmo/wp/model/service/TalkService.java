package ru.itmo.wp.model.service;

import com.google.common.base.Strings;
import ru.itmo.wp.model.domain.Talk;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.repository.TalkRepository;
import ru.itmo.wp.model.repository.UserRepository;
import ru.itmo.wp.model.repository.impl.TalkRepositoryImpl;
import ru.itmo.wp.model.repository.impl.UserRepositoryImpl;

import java.util.List;

public class TalkService {
    private final TalkRepository talkRepository = new TalkRepositoryImpl();
    private final UserRepository userRepository = new UserRepositoryImpl();

    public void validateTalk(Talk talk, String targetLogin) throws ValidationException {
        if (Strings.isNullOrEmpty(talk.getText())) {
            throw new ValidationException("Text is empty");
        }

        if (Strings.isNullOrEmpty(targetLogin)) {
            throw new ValidationException("Target is empty");
        }
        User targetUser = userRepository.findByLogin(targetLogin);
        if (targetUser == null) {
            throw new ValidationException("Can't find target");
        }
        talk.setTargetUserId(targetUser.getId());
    }

    public void saveTalk(Talk talk) {
        talkRepository.save(talk);
    }

    public List<Talk> findAll(User user) {
        return talkRepository.findAllByUserId(user.getId());
    }
}

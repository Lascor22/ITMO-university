package ru.itmo.wp.service;

import org.springframework.stereotype.Service;
import ru.itmo.wp.domain.Comment;
import ru.itmo.wp.domain.Post;
import ru.itmo.wp.domain.Tag;
import ru.itmo.wp.repository.PostRepository;
import ru.itmo.wp.repository.TagRepository;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final TagRepository tagRepository;

    public PostService(PostRepository postRepository, TagRepository tagRepository) {
        this.postRepository = postRepository;
        this.tagRepository = tagRepository;
    }

    public Post findById(Long id) {
        return id == null ? null : postRepository.findById(id).orElse(null);
    }

    public List<Post> findAll() {
        return postRepository.findAllByOrderByCreationTimeDesc();
    }

    public void writeComment(Post post, Comment comment) {
        post.addComment(comment);
        postRepository.save(post);
    }

    public void addTags(Post post, String tags) {
        List<Tag> tagsList = Arrays.stream(tags.split("\\s+")).map((String tagName) -> {
            Tag tag = tagRepository.findByName(tagName);
            if (tag == null) {
                return new Tag(tagName);
            }
            return tag;
        }).collect(Collectors.toList());
        post.setTags(tagsList);
    }
}

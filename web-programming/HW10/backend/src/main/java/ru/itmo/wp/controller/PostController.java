package ru.itmo.wp.controller;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.itmo.wp.domain.Comment;
import ru.itmo.wp.domain.Post;
import ru.itmo.wp.exception.ValidationException;
import ru.itmo.wp.form.CommentForm;
import ru.itmo.wp.service.PostService;
import ru.itmo.wp.util.BindingResultUtils;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/1")
public class PostController extends ApiController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("posts")
    public List<Post> findPosts() {
        return postService.findAll();
    }

    @PostMapping("posts")
    public void AddPost(HttpServletRequest httpServletRequest, @RequestBody @Valid Post post,
                        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException(BindingResultUtils.getErrorMessage(bindingResult));
        }
        post.setUser(getUser(httpServletRequest));
        postService.save(post);
    }

    @PostMapping("post")
    public boolean AddComment(HttpServletRequest httpServletRequest,
                              @RequestBody @Valid CommentForm commentForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException(BindingResultUtils.getErrorMessage(bindingResult));
        }
        Comment comment = new Comment();
        comment.setUser(getUser(httpServletRequest));
        comment.setText(commentForm.getText());
        postService.writeComment(getPost(commentForm.getPostId()), comment);
        return true;
    }

    private Post getPost(String postId) {
        Post post = null;
        try {
            post = postService.findById(Long.parseLong(postId));
        } catch (NumberFormatException e) {
            // no Operation
        }
        return post;
    }
}

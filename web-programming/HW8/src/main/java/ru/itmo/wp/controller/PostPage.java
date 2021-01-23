package ru.itmo.wp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itmo.wp.domain.Comment;
import ru.itmo.wp.domain.Post;
import ru.itmo.wp.service.PostService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class PostPage extends Page {
    private final PostService postService;

    public PostPage(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("post/{postId}")
    public String PostGet(@PathVariable("postId") String postId, Model model, HttpSession httpSession) {
        Post post = getPost(postId);
        model.addAttribute("post", post);
        if (getUser(httpSession) != null) {
            model.addAttribute("comment", new Comment());
        }
        return "PostPage";
    }

    @PostMapping("post/{postId}")
    public String PostPost(@PathVariable("postId") String postId, @Valid @ModelAttribute("comment") Comment comment,
                           BindingResult bindingResult, HttpSession httpSession) {
            if (bindingResult.hasErrors()) {
            return "redirect:/post/" + postId;
        }
        comment.setUser(getUser(httpSession));
        postService.writeComment(getPost(postId), comment);
        putMessage(httpSession, "You published new comment");
        return "redirect:/post/" + postId;
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

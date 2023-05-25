package com.shipsupply.controller;

import com.shipsupply.domain.Comment;
import com.shipsupply.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comment")
public class CommentController {

    @Autowired
    CommentService commentService;

    @GetMapping("/get")
    public List<Comment> getComment() {
        return commentService.getComment();
    }

    @PostMapping("/add")
    public Comment addComment(@RequestBody Comment comment) {
        return commentService.addComment(comment);
    }


    @PutMapping("/update/{id}")
    public Comment updateComment(@PathVariable Long id, @RequestBody Comment comment) {
        System.out.println("updateComment 호출" + id + "," + comment);
        return commentService.updateComment(id, comment);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteComment(@PathVariable Long id, @RequestBody Comment comment) {
        commentService.deleteComment(id, comment);
    }
}

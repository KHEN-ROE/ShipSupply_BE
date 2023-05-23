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


    @PutMapping("/update/{seq}")
    public Comment updateComment(@PathVariable Long seq, @RequestBody Comment comment) {
        System.out.println("updateComment 호출" + seq + "," + comment);
        return commentService.updateComment(seq, comment);
    }

    @DeleteMapping("/delete/{seq}")
    public void deleteComment(@PathVariable Long seq, @RequestBody Comment comment) {
        commentService.deleteComment(seq, comment);
    }
}

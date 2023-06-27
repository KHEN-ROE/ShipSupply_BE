package com.shipsupply.controller;

import com.shipsupply.domain.Comment;
import com.shipsupply.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comment")
public class CommentController {

    private static final Logger logger = LoggerFactory.getLogger(CommentController.class);

    @Autowired
    CommentService commentService;

    @Operation(summary = "댓글 보기")
    @ApiResponse(responseCode = "200", description = "댓글이 정상적으로 조회되었습니다.")
    @GetMapping("/get/{boardId}")
    public List<Comment> getComment(@PathVariable Long boardId) {
        logger.info("글번호 : " + boardId);
        return commentService.getComment(boardId);
    }

    @Operation(summary = "댓글 작성")
    @ApiResponse(responseCode = "200", description = "댓글이 정상적으로 작성되었습니다.")
    @PostMapping("/add")
    public Comment addComment(@RequestBody Comment comment) {
        return commentService.addComment(comment);
    }

    @Operation(summary = "댓글 수정")
    @ApiResponse(responseCode = "200", description = "댓글이 정상적으로 수정되었습니다.")
    @PutMapping("/update/{id}")
    public Comment updateComment(@PathVariable Long id, @RequestBody Comment comment) {
        logger.info("받은 댓글 정보 : " +  id + "," + comment);
        return commentService.updateComment(id, comment);
    }

    @Operation(summary = "댓글 삭제")
    @ApiResponse(responseCode = "200", description = "댓글이 정상적으로 삭제되었습니다.")
    @DeleteMapping("/delete/{id}")
    public void deleteComment(@PathVariable Long id, @RequestBody Comment comment) {
        logger.info("받은 정보 : " + id + "," + comment);
        commentService.deleteComment(id, comment);
    }
}

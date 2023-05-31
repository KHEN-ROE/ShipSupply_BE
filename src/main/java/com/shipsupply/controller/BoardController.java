package com.shipsupply.controller;

import com.shipsupply.domain.Board;
import com.shipsupply.domain.User;
import com.shipsupply.service.BoardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/board")
public class BoardController {

    private static final Logger logger = LoggerFactory.getLogger(BoardController.class);

    @Autowired
    BoardService bs;

    @GetMapping("/view")
    public List<Board> getList() {
        logger.info("getList 호출");
        return bs.getList();
    }

    @GetMapping("/view/{id}")
    public Board getBoard(@PathVariable Long id, @RequestParam User user) {
        logger.info("글 번호 : " + id + "," + "user정보 : " + user);
        return bs.getBoard(id);
    }

    @PostMapping("/add")
    public Board addBoard(@RequestBody Board board) {
        logger.info("받은 게시글 정보 : {}", board);
        return bs.addBoard(board);
    }

    @PutMapping("/update/{id}")
    public Board updateBoard(@PathVariable Long id, @RequestBody Board board) {
        return bs.updateBoard(id, board);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteBoard(@PathVariable Long id, @RequestBody Board board) {
        bs.deleteBoard(id, board);
    }
}

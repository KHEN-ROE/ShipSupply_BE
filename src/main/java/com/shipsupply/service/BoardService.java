package com.shipsupply.service;

import com.shipsupply.domain.Board;
import com.shipsupply.domain.User;
import com.shipsupply.persistence.BoardRepository;
import com.shipsupply.persistence.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BoardService {

    @Autowired
    BoardRepository br;

    @Autowired
    UserRepository ur;

    public List<Board> getList() {
        return br.findAll();
    }

    public Board getBoard(Long id) {
        Optional<Board> findBoard = br.findById(id);
        Board b = new Board();
        if(findBoard.isPresent()) {
            b = findBoard.get();
        }
        return b;
    }

    public Board addBoard(Board board) {
        String id = board.getUser().getId();
        Optional<User> findUser  = ur.findById(id);
        if (findUser.isPresent()) {
            User u = findUser.get();
            board.setUser(u); // user엔티티와 board 엔티티의 관계를 설정해준다.
                                //// db에 저장될 때 Board테이블의 user컬럼에 해당 사용자의 id를 외래키로 저장
            System.out.println("추가할 내용 : " + board);
            br.save(board);
        }else {
            throw new RuntimeException("해당하는 사용자 없음");
        }
        return null;
    }

    public Board updateBoard(Long id, Board board) {
        String userId = board.getUser().getId();
        Optional<User> findUser = ur.findById(userId);
        if (findUser.isPresent()) {
            User u = findUser.get();
            if(u.getId().equals(board.getUser().getId())){ // 유저정보 일치하면
                Optional<Board> findBoard = br.findById(id); // 해당 게시글 탐색
                if (findBoard.isPresent()) {
                    Board b = findBoard.get();
                    Optional.ofNullable(board.getTitle()).ifPresent(b::setTitle);
                    Optional.ofNullable(board.getText()).ifPresent(b::setText);
                    Optional.ofNullable(board.getDate()).ifPresent(b::setDate);

                    return br.save(b);
                }
            }
        }else {
            throw new RuntimeException("권한이 없습니다");
        }
        return null;
    }

    public void deleteBoard(Long id, Board board) {
        String userId = board.getUser().getId();
        Optional<User> findUser = ur.findById(userId);
        if (findUser.isPresent()) {
            User u = findUser.get();
            if(u.getId().equals(board.getUser().getId())){
                br.deleteById(id);
            }
        }
    }
}

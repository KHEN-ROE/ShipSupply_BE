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

    public Board getBoard(Long seq) {
        Optional<Board> findBoard = br.findById(seq);
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
        }else {
            throw new RuntimeException("해당하는 사용자 없음");
        }
        System.out.println("추가할 내용 : " + board);
        return br.save(board);
    }

    public Board updateBoard(Board board, Long seq) {
        String id = board.getUser().getId();
        Optional<User> findUser = ur.findById(id);
        if (findUser.isPresent()) {
            User u = findUser.get();
            if(u.getId().equals(board.getUser().getId())){
                Optional<Board> findBoard = br.findById(seq);
                if (findBoard.isPresent()) {
                    Board b = findBoard.get();
                    b.setTitle(board.getTitle());
                    b.setText(board.getText());
                    b.setDate(new Date());
                    return br.save(b);
                }
            }
        }else {
            throw new RuntimeException("권한이 없습니다");
        }
        return null;
    }

    public void deleteBoard(Long seq, Board board) {
        String id = board.getUser().getId();
        Optional<User> findUser = ur.findById(id);
        if (findUser.isPresent()) {
            User u = findUser.get();
            if(u.getId().equals(board.getUser().getId())){
                br.deleteById(seq);
            }
        }
    }
}

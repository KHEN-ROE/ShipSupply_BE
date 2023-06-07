package com.shipsupply.service;

import com.shipsupply.domain.Comment;
import com.shipsupply.domain.User;
import com.shipsupply.persistence.CommentRepository;
import com.shipsupply.persistence.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder encoder;

    // 댓글은 모든 사람이 다 볼 수 있어야 함. 근데 비밀 댓글인 경우?(이건 나중에) + 대댓글은?
    public List<Comment> getComment(Long boardId) {
        return commentRepository.findAllByBoardIdOrderByHitCountDesc(boardId);
    }

    public Comment addComment(Comment comment) {
        // 댓글 작성자가 db에 있는 회원인지 확인
        Optional<User> findUser = userRepository.findById(comment.getUser().getId());
        if (findUser.isPresent()) {
            User user = findUser.get();
            if(user.getId().equals(comment.getUser().getId())){
                return commentRepository.save(comment);
            }
            else {
                throw new RuntimeException("권한이 없습니다");
            }
        }else {
            throw new RuntimeException("존재하지 않는 회원");
        }
    }

    public Comment updateComment(Long id, Comment comment) {

        Optional<User> findUser = userRepository.findById(comment.getUser().getId());
        if (findUser.isPresent()) {
            User user = findUser.get();
            if(comment.getUser().getId().equals(user.getId())) { // 파라미터로 받은 값과 db에 있는 유저 비교
                Optional<Comment> c = commentRepository.findById(id);
                if (c.isPresent()) {
                    Comment co = c.get();
                    Optional.ofNullable(comment.getText()).ifPresent(co::setText);
                    Optional.ofNullable(comment.getDate()).ifPresent(co::setDate);
//                    Optional.ofNullable(comment.getHitCount()).ifPresent(co::setHitCount); // 초깃값을 0L로 설정해두었기 때문에 없어도 됨
                    return commentRepository.save(co);
                }else {
                    throw new RuntimeException("존재하지 않는 댓글");
                }
            }else {
                throw new RuntimeException("사용자 정보 불일치");
            }
        }else {
            throw new RuntimeException("존재하지 않는 사용자");
        }
    }

    public void deleteComment(Long id, Comment comment) {
        Optional<User> findUser = userRepository.findById(comment.getUser().getId());
        if (findUser.isPresent()) {
            User user = findUser.get();
            if(comment.getUser().getId().equals(user.getId())){
                commentRepository.deleteById(id);
            }
        }
    }

}

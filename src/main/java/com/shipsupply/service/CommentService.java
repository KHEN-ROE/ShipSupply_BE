package com.shipsupply.service;

import com.shipsupply.domain.Comment;
import com.shipsupply.domain.User;
import com.shipsupply.persistence.CommentRepository;
import com.shipsupply.persistence.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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
    public List<Comment> getComment() {
        return commentRepository.findAllByOrderByHitCountDesc();
    }

    public Comment addComment(Comment comment) {
        // 댓글 작성자가 db에 있는 회원인지 확인
        Optional<User> findUser = userRepository.findById(comment.getUser().getId());
        if (findUser.isPresent()) {
            User user = findUser.get();
            if(user.getId().equals(comment.getUser().getId())){
                commentRepository.save(comment);
            }
            else {
                throw new RuntimeException("권한이 없습니다");
            }
        }else {
            throw new RuntimeException("존재하지 않는 회원");
        }
        return comment;
    }

    public Comment updateComment(Long seq, Comment comment) {

        Optional<User> findUser = userRepository.findById(comment.getUser().getId());
        Comment co = new Comment();
        if (findUser.isPresent()) {
            User user = findUser.get();
            if(encoder.matches(comment.getUser().getPassword(), user.getPassword())) {
                Optional<Comment> c = commentRepository.findById(seq);
                if (c.isPresent()) {
                    co = c.get();
                    co.setText(comment.getText());
                    co.setDate(comment.getDate());
                    commentRepository.save(co);
                }
            }
        }
        return co;
    }

    public void deleteComment(Long seq, Comment comment) {
        Optional<User> findUser = userRepository.findById(comment.getUser().getId());
        if (findUser.isPresent()) {
            User user = findUser.get();
            if(encoder.matches(comment.getUser().getPassword(), user.getPassword())){
                commentRepository.deleteById(seq);
            }
        }
    }

}

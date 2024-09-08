package com.boardpractice.service;

import com.boardpractice.domain.dto.CommentDTO;
import com.boardpractice.domain.entity.Comment;
import com.boardpractice.global.CommentNotFoundException;
import com.boardpractice.repository.CommentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentService {

    private final CommentRepository commentRepository;

    // 댓글 전체 조회
    public List<Comment> findAllComments() {
        return commentRepository.findAll();
    }

    // 댓글 단건 조회
    public CommentDTO getCommentByCommentNo(long commentNo) throws CommentNotFoundException {

        Comment comment = commentRepository.findById(commentNo)
                .orElseThrow(() -> new CommentNotFoundException("해당 값이 없습니다~" + commentNo));

        return CommentDTO.builder()
                .commentNo(comment.getCommentNo())
                .commentContent(comment.getCommentContent())
                .build();
    }

    @Transactional
    public void registComment(CommentDTO newComment) {

        Comment comment = Comment.builder()
                .commentNo(newComment.getCommentNo())
                .commentContent(newComment.getCommentContent())
                .build();

        commentRepository.save(comment);
    }

    @Transactional
    public void updateComment(long commentNo, String commentContent) {
        Comment comment = commentRepository.findById(commentNo)
                .orElseThrow(() -> new RuntimeException("해당 번호의 댓글을 찾을 수 없음"));


        comment.setCommentNo(commentNo);
        comment.setCommentContent(commentContent);

        commentRepository.save(comment);
    }

    public boolean deleteComment(long commentNo) {
        try {
            if (commentRepository.existsById(commentNo)) {
                commentRepository.deleteById(commentNo);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }
}

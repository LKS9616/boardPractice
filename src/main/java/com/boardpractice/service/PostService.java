package com.boardpractice.service;

import com.boardpractice.domain.dto.PostDTO;
import com.boardpractice.domain.entity.Post;
import com.boardpractice.global.PostNotFoundException;
import com.boardpractice.repository.PostRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostService {
    private final PostRepository postRepository;

    // 게시물 전체 조회
    public List<Post> findAllPosts() {
        return postRepository.findAll();
    }

    // 게시글 단일 조회
    public PostDTO getPostByPostNo(long postNo) throws PostNotFoundException {
        Post post =  postRepository.findById(postNo)
                .orElseThrow(() -> new PostNotFoundException("값이 없어요~" + postNo));

        return PostDTO.builder()
                .postNo(post.getPostNo())
                .title(post.getTitle())
                .content(post.getContent())
                .build();
    }

    // 게시글 새 등록
    @Transactional
    public void registPost(PostDTO newPost) {
        Post post = Post.builder()
                .postNo(newPost.getPostNo())
                .title(newPost.getTitle())
                .content(newPost.getContent())
                .build();

        postRepository.save(post);
    }

    // 게시글 수정
    @Transactional
    public void updatePost(long postNo, String title, String content) {
        Post post = postRepository.findById(postNo)
                .orElseThrow(() -> new RuntimeException("해당 번호의 댓글을 찾을 수 없음"));

        post.setTitle(title);
        post.setContent(content);


        postRepository.save(post);
    }

    // 게시물 삭제 성공
    public boolean deletePost(long postNo) {
        try {
            if (postRepository.existsById(postNo)) {
                postRepository.deleteById(postNo);
                return true; 
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }


}
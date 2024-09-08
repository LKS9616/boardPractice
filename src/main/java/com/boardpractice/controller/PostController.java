package com.boardpractice.controller;

import com.boardpractice.common.ResponseMsg;
import com.boardpractice.domain.dto.PostDTO;
import com.boardpractice.domain.entity.Post;
import com.boardpractice.global.PostNotFoundException;
import com.boardpractice.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.nio.charset.Charset;

import java.util.List;


@Tag(name = "RestAPI 테스트용 보드")
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/board")
public class PostController {

    private final PostService postService;

    // 게시글 작성
    @Operation(summary = "게시글 작성", description = "게시판에 업로드할 새로운 게시글 작성")
    @PostMapping("/posts")
    public ResponseEntity<ResponseMsg> createNewPost(@RequestBody PostDTO newPost) {

        postService.registPost(newPost);

        String successMsg = "게시글 등록에 성공하였습니다.";

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("result", successMsg);

        return ResponseEntity.ok().body(new ResponseMsg(201, "게시글 작성 성공", responseMap));
    }

    // 게시글 전체 조회
    @Operation(summary = "게시글 전체 조회", description = "사이트의 게시글 전체 조회")
    @GetMapping("/posts")
    public ResponseEntity<ResponseMsg> findAllPosts() {

        List<Post> posts = postService.findAllPosts();

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("posts", posts);

        return ResponseEntity.ok().body(new ResponseMsg(202, "게시물 전체 조회성공", responseMap));
    }

    // 게시글 단건 조회
    @Operation(
            summary = "게시글 번호로 특정 게시글 조회",
            description = "게시글 번호를 통해 특정 게시글을 조회한다.",
            parameters = {
                    @Parameter(
                            name = "postNo",
                            description = "사용자 화면에서 넘어오는 post의 pk"
                    )
            })
    @GetMapping("/posts/{postNo}")
    public ResponseEntity<ResponseMsg> findPostByPostNo(@PathVariable long postNo) throws PostNotFoundException {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(
                new MediaType(
                        "application",
                        "json",
                        Charset.forName("UTF-8")
                )
        );



        PostDTO foundPost = postService.getPostByPostNo(postNo);

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("post", foundPost);


        return ResponseEntity.ok().headers(headers).body(new ResponseMsg(203, "게시글 단일 조회성공", responseMap));

    }

    // 게시글 수정
    @Operation(summary = "게시글 수정", description = "특정 게시글 수정")
    @ApiResponses({
            @ApiResponse(responseCode = "203", description = "게시글 수정 성공"),
            @ApiResponse(responseCode = "400", description = "잘못 입력된 파라미터")
    })
    @PutMapping("/posts/{postNo}")
    public ResponseEntity<?> editPost(@PathVariable long postNo, @RequestBody PostDTO modifiedPost) throws PostNotFoundException {
        postService.updatePost(postNo, modifiedPost.getTitle(), modifiedPost.getContent());

        Map<String, Object> responseMap = new HashMap<>();
        String msg = "게시글 수정에 성공하였습니다.";
        responseMap.put("result", msg);

        return ResponseEntity.ok().body(new ResponseMsg(204, "게시글 수정 성공", responseMap));
    }

    // 게시글 삭제
    @Operation(summary = "게시글 삭제", description = "게시글 삭제")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "게시글 삭제 성공"),
            @ApiResponse(responseCode = "400", description = "잘못 입력된 파라미터")
    })
    @DeleteMapping("/posts/{postNo}")
    public ResponseEntity<?> deletePost(@PathVariable long postNo) throws PostNotFoundException {
        Map<String, Object> responseMap = new HashMap<>();

        boolean isDeleted = postService.deletePost(postNo);
        if (isDeleted) {
            String msg = "게시글 삭제에 성공하였습니다.";
            responseMap.put("result", msg);
        } else {
            throw new PostNotFoundException("게시글 삭제에 실패하였습니다.");
        }

        return ResponseEntity.ok().body(new ResponseMsg(205, "게시글 삭제 성공", responseMap));
    }
}
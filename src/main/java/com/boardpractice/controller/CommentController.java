package com.boardpractice.controller;

import com.boardpractice.common.ResponseMsg;
import com.boardpractice.domain.dto.CommentDTO;
import com.boardpractice.domain.entity.Comment;
import com.boardpractice.global.CommentNotFoundException;
import com.boardpractice.service.CommentService;
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

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Tag(name = "Spring Boot Swagger 연동 API (Board)")
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/board")
public class CommentController {

    private final CommentService commentService;

    // 댓글 작성
    @Operation(summary = "새 댓글 작성", description = "게시물에 달 새로운 댓글 작성")
    @PostMapping("/comments")
    public ResponseEntity<ResponseMsg> createNewComment(@RequestBody CommentDTO newComment) {

        commentService.registComment(newComment);

        String successMsg = "댓글 등록에 성공하였습니다.";

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("result", successMsg);

        return ResponseEntity.ok().body(new ResponseMsg(206, "댓글 작성 성공", responseMap));
    }

    // 댓글 전체 조회
    @Operation(summary = "댓글 전체 조회", description = "사이트의 댓글 전체 조회")
    @GetMapping("/comments")
    public ResponseEntity<ResponseMsg> findAllComments() {

        List<Comment> comments = commentService.findAllComments();

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("comments", comments);

        return ResponseEntity.ok().body(new ResponseMsg(207, "댓글 전체 조회 성공", responseMap));
    }

    // 단일 댓글 조회
    @Operation(
            summary = "댓글 번호로 특정 댓글 조회",
            description = "댓글 번호를 통해 특정 댓글을 조회한다.",
            parameters = {
                    @Parameter(
                            name = "commentNo",
                            description = "사용자 화면에서 넘어오는 comment의 pk"
                    )
            })
    @GetMapping("/comments/{commentNo}")
    public ResponseEntity<ResponseMsg> findCommentByCommentNo(@PathVariable long commentNo) throws CommentNotFoundException {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(
                new MediaType(
                        "application",
                        "json",
                        Charset.forName("UTF-8")
                )
        );



        CommentDTO foundComment = commentService.getCommentByCommentNo(commentNo);

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("comment", foundComment);


        return ResponseEntity.ok().headers(headers).body(new ResponseMsg(208, "댓글 단일 조회 성공", responseMap));


    }

    // 댓글 수정
    @Operation(summary = "댓글 수정", description = "특정 댓글 수정")
    @ApiResponses({
            @ApiResponse(responseCode = "209", description = "댓글 수정 성공"),
            @ApiResponse(responseCode = "400", description = "잘못 입력된 파라미터")
    })
    @PutMapping("/comments/{commentNo}")
    public ResponseEntity<?> editComment(@PathVariable long commentNo, @RequestBody CommentDTO modifiedComment) throws CommentNotFoundException {
        commentService.updateComment(commentNo, modifiedComment.getCommentContent());

        Map<String, Object> responseMap = new HashMap<>();
        String msg = "댓글 수정에 성공하였습니다.";
        responseMap.put("result", msg);

        return ResponseEntity.ok().body(new ResponseMsg(209, "댓글 수정 성공", responseMap));
    }

    // 댓글 삭제
    @Operation(summary = "댓글 삭제", description = "댓글 삭제")
    @ApiResponses({
            @ApiResponse(responseCode = "210", description = "댓글 삭제 성공"),
            @ApiResponse(responseCode = "400", description = "잘못 입력된 파라미터")
    })
    @DeleteMapping("/comments/{commentNo}")
    public ResponseEntity<?> deleteComment(@PathVariable long commentNo) throws CommentNotFoundException {
        Map<String, Object> responseMap = new HashMap<>();

        boolean isDeleted = commentService.deleteComment(commentNo);
        if (isDeleted) {
            String msg = "댓글 삭제에 성공하였습니다.";
            responseMap.put("result", msg);
        } else {
            throw new CommentNotFoundException("댓글 삭제에 실패하였습니다.");
        }

        return ResponseEntity.ok().body(new ResponseMsg(210, "댓글 삭제 성공", responseMap));
    }
}




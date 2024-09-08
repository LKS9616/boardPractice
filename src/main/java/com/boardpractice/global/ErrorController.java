package com.boardpractice.global;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErrorController {
    @ExceptionHandler(PostNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserFindException(PostNotFoundException e){
        String errorCode = "ERROR_CODE_0001";
        String errorDescription = "존재하지 않는 게시글입니다.";
        String errorDetail = e.getMessage();

        return new ResponseEntity<>(
                new ErrorResponse(errorCode, errorDescription, errorDetail),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(CommentNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserFindException(CommentNotFoundException e){
        String errorCode = "ERROR_CODE_0002";
        String errorDescription = "존재하지 않는 댓글입니다.";
        String errorDetail = e.getMessage();

        return new ResponseEntity<>(
                new ErrorResponse(errorCode, errorDescription, errorDetail),
                HttpStatus.NOT_FOUND
        );
    }
}

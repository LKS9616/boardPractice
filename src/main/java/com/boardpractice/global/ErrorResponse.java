package com.boardpractice.global;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ErrorResponse {
    private String errorCode;
    private String errorDescription;
    private String errorDetail;
}
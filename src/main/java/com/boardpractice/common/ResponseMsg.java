package com.boardpractice.common;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ResponseMsg {

    private int httpStatusCode;
    private String message;
    private Object results;
}

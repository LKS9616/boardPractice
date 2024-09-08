package com.boardpractice.domain.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class CommentDTO {
    private long commentNo;
    private String commentContent;

}

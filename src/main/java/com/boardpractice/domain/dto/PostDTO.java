package com.boardpractice.domain.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class PostDTO {
    private long postNo;
    private String title;
    private String content;
}

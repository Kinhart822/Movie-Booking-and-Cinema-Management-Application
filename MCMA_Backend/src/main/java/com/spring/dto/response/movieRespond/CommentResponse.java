package com.spring.dto.response.movieRespond;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentResponse {
    private String movieName;
    private String content;
}

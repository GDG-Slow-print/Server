package com.site.slowprint.board.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class MatchingListResponseDTO {
    private Long matchingId;
    private String userEmail;
    private String title;
    private String contents;
    private String mainPhoto;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String status;
}

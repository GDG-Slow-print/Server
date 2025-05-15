package com.site.slowprint.board.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class MatchingDetailResponseDTO {
    private String userEmail;
    private String title;
    private String contents;
    private List<PhotoDTO> photos;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String status;
}

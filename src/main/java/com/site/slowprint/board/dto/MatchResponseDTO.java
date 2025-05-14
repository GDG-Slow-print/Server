package com.site.slowprint.board.dto;

import lombok.Builder;

@Builder
public class MatchResponseDTO {
    private Long matchId;
    private String message;
}

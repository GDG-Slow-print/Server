package com.site.slowprint.board.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PhotoDTO {
    private String url;
    private boolean isMain;
    private int order;
}

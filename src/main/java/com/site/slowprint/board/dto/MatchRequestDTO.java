package com.site.slowprint.board.dto;

import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class MatchRequestDTO {

    private String title;

    private String contents;

    private String nation;

    private List<PhotoDTO> photos = new ArrayList<>();
}

package com.site.slowprint.board.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class RecruitmentRequestDTO {
    private String title;

    private String contents;

    private String nation;

    private String province;

    private String city;

    private List<PhotoDTO> photos = new ArrayList<>();
}

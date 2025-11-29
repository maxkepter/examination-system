package com.examination_system.model.dto.common;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class QuestionTemplate {
    private String chapterNo;
    private String difficulty;
    private int amount;

}
package com.examination_system.controller.admin;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.examination_system.model.dto.common.QuestionDTO;
import com.examination_system.service.exam.QuestionService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/question")
@RequiredArgsConstructor
public class QuestionController {
    // This controller will handle requests related to questions in the examination
    // system.
    // You can define methods here to handle various endpoints related to questions.
    private final QuestionService questionService;

    @PostMapping()
    public String createQuestion(@RequestBody QuestionDTO questionDTO) {
        questionService.createQuestion(questionDTO);
        return "Question created successfully";
    }

}

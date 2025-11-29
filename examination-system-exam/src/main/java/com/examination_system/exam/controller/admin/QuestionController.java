package com.examination_system.exam.controller.admin;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.examination_system.exam.model.dto.common.QuestionDTO;
import com.examination_system.exam.service.QuestionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/question")
@RequiredArgsConstructor
@Tag(name = "Câu hỏi", description = "API quản trị cho câu hỏi")
public class QuestionController {
    private final QuestionService questionService;

    @PostMapping()
    @Operation(summary = "Tạo câu hỏi", description = "Thêm mới câu hỏi cùng các phương án trả lời")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Tạo thành công", content = @Content),
            @ApiResponse(responseCode = "400", description = "Dữ liệu không hợp lệ", content = @Content)
    })
    public ResponseEntity<?> createQuestion(@Valid @RequestBody QuestionDTO questionDTO) {
        questionService.createQuestion(questionDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}

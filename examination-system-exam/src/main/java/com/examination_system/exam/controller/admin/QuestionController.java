package com.examination_system.exam.controller.admin;

import com.examination_system.exam.model.dto.common.QuestionDto;
import com.examination_system.exam.model.mapper.QuestionMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.examination_system.exam.service.QuestionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequestMapping("/admin/question")
@RequiredArgsConstructor
@Tag(name = "Câu hỏi", description = "API quản trị cho câu hỏi")
public class QuestionController {
    private final QuestionService questionService;
    private final QuestionMapper questionMapper;

    @PostMapping()
    @Operation(summary = "Tạo câu hỏi", description = "Thêm mới câu hỏi cùng các phương án trả lời")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Tạo thành công", content = @Content),
            @ApiResponse(responseCode = "400", description = "Dữ liệu không hợp lệ", content = @Content)
    })
    public ResponseEntity<?> createQuestion(@Valid @RequestBody QuestionDto questionDTO) {
        questionService.createQuestion(questionDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping()
    @Operation(summary = "Danh sách câu hỏi", description = "Lấy toàn bộ câu hỏi")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Thành công", content = @Content)
    })
    public ResponseEntity<List<QuestionDto>> getAllQuestion() {
        List<QuestionDto> questionDtos = questionService.getAllQuestions().stream().map(questionMapper::toDTO).toList();
        return ResponseEntity.ok(questionDtos);
    }

    @PatchMapping("{questionId}")
    @Operation(summary = "Cập nhật câu hỏi", description = "Cập nhật thông tin câu hỏi và các phương án trả lời")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cập nhật thành công", content = @Content),
            @ApiResponse(responseCode = "404", description = "Câu hỏi không tồn tại", content = @Content)
    })
    public ResponseEntity<QuestionDto> updateQuestion(@PathVariable Long questionId,
            @Valid @RequestBody QuestionDto questionDTO) {
        try {
            // Set questionId from path variable to DTO before converting to entity
            questionDTO.setQuestionId(questionId);
            QuestionDto updatedQuestion = questionMapper
                    .toDTO(questionService.updateQuestion(questionMapper.toEntity(questionDTO)));
            return ResponseEntity.ok(updatedQuestion);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("{questionId}")
    @Operation(summary = "Xóa câu hỏi", description = "Xóa câu hỏi theo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Xóa thành công", content = @Content),
            @ApiResponse(responseCode = "404", description = "Câu hỏi không tồn tại", content = @Content)
    })
    public ResponseEntity<?> deleteQuestion(@PathVariable Long questionId) {
        try {
            questionService.deleteQuestion(questionId);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/filter")
    @Operation(summary = "Lọc câu hỏi", description = "Lọc câu hỏi theo độ khó, chương hoặc môn học")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Thành công", content = @Content)
    })
    public ResponseEntity<List<QuestionDto>> filterQuestions(@RequestParam(required = false) Integer difficulty,
            @RequestParam(required = false) Long chapterId,
            @RequestParam(required = false) String subjectCode) {
        List<QuestionDto> results = questionService.filterQuestions(difficulty, chapterId, subjectCode)
                .stream()
                .map(questionMapper::toDTO)
                .toList();
        return ResponseEntity.ok(results);
    }

}

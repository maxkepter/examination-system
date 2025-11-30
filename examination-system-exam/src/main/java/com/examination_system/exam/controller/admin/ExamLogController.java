package com.examination_system.exam.controller.admin;

import com.examination_system.exam.model.dto.response.ExamLogResponse;
import com.examination_system.exam.model.mapper.ExamLogMapper;
import com.examination_system.exam.service.ExamLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/exam-log")
@RequiredArgsConstructor
@Tag(name = "Nhật ký thi (Admin)", description = "API quản trị xem nhật ký làm bài thi của học viên")
public class ExamLogController {
    
    private final ExamLogService examLogService;
    private final ExamLogMapper examLogMapper;

    @GetMapping("/student-exam/{studentExamId}")
    @Operation(summary = "Xem nhật ký thi", description = "Lấy danh sách nhật ký làm bài thi của một student exam cụ thể")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Thành công",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ExamLogResponse.class)))),
            @ApiResponse(responseCode = "404", description = "Không tìm thấy", content = @Content)
    })
    public ResponseEntity<?> getExamLogsByStudentExam(@PathVariable Long studentExamId) {
        try {
            var examLogs = examLogService.getExamLogsByStudentExamId(studentExamId);
            var responses = examLogs.stream()
                    .map(examLogMapper::toResponse)
                    .toList();
            return ResponseEntity.ok(responses);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Student exam not found");
        }
    }
}

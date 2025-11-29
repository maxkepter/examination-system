package com.examination_system.exam.controller.admin;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.examination_system.exam.model.dto.common.StudentExamDTO;
import com.examination_system.exam.service.ExamHistoryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path = "/admin/student-exam")
@RequiredArgsConstructor
@Tag(name = "Lịch sử bài thi (Admin)", description = "API quản trị xem lịch sử bài thi của người dùng")
public class UserExamHistoryController {
    private final ExamHistoryService examHistoryService;

    @GetMapping()
    @Operation(summary = "Danh sách tất cả lịch sử bài thi", description = "Lấy toàn bộ lịch sử bài thi")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Thành công",
            content = @Content(mediaType = "application/json",
                array = @ArraySchema(schema = @Schema(implementation = StudentExamDTO.class))))
    })
    public ResponseEntity<List<StudentExamDTO>> getAllExamHistory() {
        return ResponseEntity.ok(examHistoryService.getAll());
    }

    @GetMapping(path = "/exam/{examId}")
    @Operation(summary = "Lịch sử theo kỳ thi", description = "Lấy lịch sử theo ID kỳ thi")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Thành công",
            content = @Content(mediaType = "application/json",
                array = @ArraySchema(schema = @Schema(implementation = StudentExamDTO.class)))),
        @ApiResponse(responseCode = "400", description = "Kỳ thi không hợp lệ hoặc không tồn tại", content = @Content)
    })
    public ResponseEntity<List<StudentExamDTO>> getAllExamHistoryByExam(@PathVariable Long examId) {
     try {
        return ResponseEntity.ok(examHistoryService.getByExam(examId));
     } catch (IllegalArgumentException ex) {
        return ResponseEntity.badRequest().build();
     }
    }

    @GetMapping(path = "/student/{userId}")
    @Operation(summary = "Lịch sử theo người dùng", description = "Lấy lịch sử theo ID người dùng")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Thành công",
            content = @Content(mediaType = "application/json",
                array = @ArraySchema(schema = @Schema(implementation = StudentExamDTO.class))))
    })
    public ResponseEntity<List<StudentExamDTO>> getAllExamHistoryByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(examHistoryService.getByUserId(userId));
    }
}

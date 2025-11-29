package com.examination_system.exam.controller.student;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.examination_system.exam.model.dto.common.StudentExamDTO;
import com.examination_system.exam.model.dto.response.StudentExamResponse;
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
@RequestMapping(path = "/student/exam")
@RequiredArgsConstructor
@Tag(name = "Lịch sử bài thi", description = "API học viên xem lịch sử và chi tiết bài thi của mình")
public class UserExamController {
    private final ExamHistoryService examHistoryService;

    @GetMapping(path = "/history")
    @Operation(summary = "Xem lịch sử", description = "Lấy danh sách bài thi của người dùng hiện tại")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Thành công",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = StudentExamDTO.class))))
    })
    public ResponseEntity<List<StudentExamDTO>> getExamHistory() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        List<StudentExamDTO> list = examHistoryService.getByUsername(username);
        return ResponseEntity.ok(list);
    }

    @GetMapping(path = "/view/{studentExamId}")
    @Operation(summary = "Xem chi tiết bài thi", description = "Lấy chi tiết một bài thi của người dùng hiện tại")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Thành công",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StudentExamResponse.class))),
            @ApiResponse(responseCode = "404", description = "Không tìm thấy", content = @Content),
            @ApiResponse(responseCode = "403", description = "Không có quyền truy cập", content = @Content),
            @ApiResponse(responseCode = "400", description = "Yêu cầu không hợp lệ", content = @Content)
    })
    public ResponseEntity<StudentExamResponse> getStudentExam(@PathVariable Long studentExamId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        try {
            StudentExamResponse resp = examHistoryService.getStudentExam(username, studentExamId);
            return ResponseEntity.ok(resp);
        } catch (RuntimeException ex) {
            String msg = ex.getMessage() != null ? ex.getMessage().toLowerCase() : "";
            if (msg.contains("not found")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            if (msg.contains("permission")) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}

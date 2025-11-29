package com.examination_system.exam.controller.admin;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.examination_system.exam.model.dto.response.StudentExamResponse;
import com.examination_system.model.entity.exam.student.StudentExam;
import com.examination_system.exam.model.mapper.StudentExamMapper;
import com.examination_system.exam.service.StudentExamService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/admin/exam/student-exam")
@RequiredArgsConstructor
@Tag(name = "Bài thi học viên (Admin)", description = "API quản trị xem chi tiết bài thi của học viên")
public class StudentExamController {

    private final StudentExamService studentExamService;
    private final StudentExamMapper studentExamMapper;

    @GetMapping("/{studentExamId}")
    @Operation(summary = "Xem chi tiết bài thi học viên", description = "Lấy chi tiết bài thi theo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Thành công",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StudentExamResponse.class))),
            @ApiResponse(responseCode = "404", description = "Không tìm thấy", content = @Content)
    })
    public ResponseEntity<StudentExamResponse> getStudentExam(@PathVariable Long studentExamId) {
        StudentExam studentExam = studentExamService.getStudentExamById(studentExamId);
        StudentExamResponse response = studentExamMapper.toResponse(studentExam);
        return ResponseEntity.ok(response);
    }

}

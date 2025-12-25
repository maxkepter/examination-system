package com.examination_system.exam.controller.admin;

import com.examination_system.exam.model.dto.common.ExamDto;
import com.examination_system.exam.model.dto.request.ExamCreationRequest;
import com.examination_system.exam.model.dto.response.ExamResponse;
import com.examination_system.exam.service.ExamService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/exam")
@RequiredArgsConstructor
@Tag(name = "Quản lý đề thi", description = "API quản trị đề thi")
public class ExamController {
    
    private final ExamService examService;

    @PostMapping
    @Operation(summary = "Tạo đề thi", description = "Tạo đề thi mới từ các mẫu câu hỏi")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Tạo thành công",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExamResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dữ liệu không hợp lệ", content = @Content),
            @ApiResponse(responseCode = "401", description = "Chưa xác thực", content = @Content)
    })
    public ResponseEntity<?> createExam(@Valid @RequestBody ExamCreationRequest request) {
        try {
            ExamResponse response = examService.createExam(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error creating exam: " + ex.getMessage());
        }
    }

    @GetMapping
    @Operation(summary = "Danh sách đề thi", description = "Lấy danh sách tất cả đề thi")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Thành công",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ExamResponse.class))))
    })
    public ResponseEntity<List<ExamResponse>> getAllExams() {
        List<ExamResponse> exams = examService.getAllExams();
        return ResponseEntity.ok(exams);
    }

    @GetMapping("/{examId}")
    @Operation(summary = "Chi tiết đề thi", description = "Lấy thông tin chi tiết đề thi theo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Thành công",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExamResponse.class))),
            @ApiResponse(responseCode = "404", description = "Không tìm thấy đề thi", content = @Content)
    })
    public ResponseEntity<?> getExamById(@PathVariable Long examId) {
        try {
            ExamResponse exam = examService.getExamById(examId);
            return ResponseEntity.ok(exam);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Exam not found");
        }
    }

    @GetMapping("/code/{examCode}")
    @Operation(summary = "Tìm đề thi theo mã", description = "Lấy thông tin đề thi theo exam code")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Thành công",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExamResponse.class))),
            @ApiResponse(responseCode = "404", description = "Không tìm thấy đề thi", content = @Content)
    })
    public ResponseEntity<?> getExamByCode(@PathVariable String examCode) {
        try {
            ExamResponse exam = examService.getExamByCode(examCode);
            return ResponseEntity.ok(exam);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Exam not found");
        }
    }

    @PutMapping("/{examId}")
    @Operation(summary = "Cập nhật đề thi", description = "Cập nhật thông tin đề thi")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cập nhật thành công",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExamResponse.class))),
            @ApiResponse(responseCode = "404", description = "Không tìm thấy đề thi", content = @Content),
            @ApiResponse(responseCode = "400", description = "Dữ liệu không hợp lệ", content = @Content)
    })
    public ResponseEntity<?> updateExam(@PathVariable Long examId, @Valid @RequestBody ExamDto examDto) {
        try {
            ExamResponse updatedExam = examService.updateExam(examId, examDto);
            return ResponseEntity.ok(updatedExam);
        } catch (RuntimeException ex) {
            String msg = ex.getMessage() != null ? ex.getMessage().toLowerCase() : "";
            if (msg.contains("not found")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Exam not found");
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error updating exam: " + ex.getMessage());
        }
    }

    @DeleteMapping("/{examId}")
    @Operation(summary = "Xóa đề thi", description = "Xóa đề thi (soft delete)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Xóa thành công", content = @Content),
            @ApiResponse(responseCode = "404", description = "Không tìm thấy đề thi", content = @Content)
    })
    public ResponseEntity<?> deleteExam(@PathVariable Long examId) {
        try {
            examService.deleteExam(examId);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Exam not found");
        }
    }

    @GetMapping("/subject/{subjectCode}")
    @Operation(summary = "Tìm đề thi theo môn học", description = "Lấy danh sách đề thi theo subject code")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Thành công",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ExamResponse.class))))
    })
    public ResponseEntity<List<ExamResponse>> getExamsBySubject(@PathVariable String subjectCode) {
        List<ExamResponse> exams = examService.getExamsBySubject(subjectCode);
        return ResponseEntity.ok(exams);
    }
}

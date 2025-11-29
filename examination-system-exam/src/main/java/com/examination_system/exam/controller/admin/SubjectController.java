package com.examination_system.exam.controller.admin;

import com.examination_system.exam.model.dto.common.SubjectDto;
import com.examination_system.exam.model.dto.response.SubjectRespone;
import com.examination_system.model.entity.exam.Subject;

import jakarta.validation.Valid;

import com.examination_system.exam.model.mapper.SubjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.examination_system.exam.model.dto.request.SubjectCreationRequest;
import com.examination_system.exam.service.SubjectService;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequestMapping("/admin/subject")
@RequiredArgsConstructor
@Tag(name = "Môn học", description = "API quản trị cho môn học")
public class SubjectController {
    private final SubjectService subjectService;
    private final SubjectMapper subjectMapper;

    @PostMapping
    @Operation(summary = "Tạo môn học", description = "Thêm mới môn học cùng chương và chuyên ngành liên quan")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Tạo thành công",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SubjectRespone.class))),
            @ApiResponse(responseCode = "400", description = "Yêu cầu không hợp lệ", content = @Content)
    })
    public ResponseEntity<SubjectRespone> addSubject(@Valid @RequestBody SubjectCreationRequest request) {
        try {
            Subject subject = subjectService.addSubject(request);
            SubjectRespone body = subjectMapper.toResponse(subject);
            return ResponseEntity.status(HttpStatus.CREATED).body(body);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping
    @Operation(summary = "Danh sách môn học", description = "Lấy toàn bộ môn học")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Thành công",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = SubjectRespone.class))))
    })
    public ResponseEntity<List<SubjectRespone>> getAllSubjects() {
        List<SubjectRespone> subjects = subjectService.getAllSubjects().stream()
                .map(subjectMapper::toResponse)
                .toList();
        return ResponseEntity.ok(subjects);
    }

    @GetMapping("/{subjectCode}")
    @Operation(summary = "Chi tiết môn học", description = "Lấy chi tiết theo mã môn học")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Thành công",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SubjectRespone.class))),
            @ApiResponse(responseCode = "404", description = "Không tìm thấy", content = @Content)
    })
    public ResponseEntity<SubjectRespone> getSubjectById(@PathVariable String subjectCode) {
        try {
            Subject subject = subjectService.getSubjectByCode(subjectCode);
            return ResponseEntity.ok(subjectMapper.toResponse(subject));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/major/{majorCode}")
    @Operation(summary = "Danh sách môn theo chuyên ngành", description = "Lấy các môn thuộc một chuyên ngành")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Thành công",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = SubjectRespone.class))))
    })
    public ResponseEntity<List<SubjectRespone>> getSubjectsByMajor(@PathVariable String majorCode) {
        List<SubjectRespone> subjects = subjectService.getSubjectsByMajor(majorCode).stream()
                .map(subjectMapper::toResponse)
                .toList();
        return ResponseEntity.ok(subjects);
    }

    @PatchMapping("/{subjectCode}")
    @Operation(summary = "Cập nhật môn học", description = "Cập nhật thông tin môn học theo mã")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cập nhật thành công",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SubjectDto.class))),
            @ApiResponse(responseCode = "404", description = "Không tìm thấy", content = @Content),
            @ApiResponse(responseCode = "400", description = "Yêu cầu không hợp lệ", content = @Content)
    })
    public ResponseEntity<SubjectDto> updateSubject(@PathVariable String subjectCode,
            @Valid @RequestBody SubjectCreationRequest request) {
        try {
            Subject subject = subjectService.toEntity(request);
            subject.setSubjectCode(subjectCode);
            Subject updated = subjectService.updateSubject(subject);
            return ResponseEntity.ok(subjectMapper.toDto(updated));
        } catch (IllegalArgumentException ex) {
            // If not found or invalid, map to 404 or 400 depending on message
            String msg = ex.getMessage() != null ? ex.getMessage() : "";
            if (msg.toLowerCase().contains("not found")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

}

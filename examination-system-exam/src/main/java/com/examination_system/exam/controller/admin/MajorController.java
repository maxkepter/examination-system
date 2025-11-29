package com.examination_system.exam.controller.admin;

import java.util.List;

import com.examination_system.exam.model.dto.common.MajorDto;
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

import com.examination_system.model.entity.exam.Major;
import com.examination_system.exam.model.mapper.MajorMapper;
import com.examination_system.repository.exam.MajorRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/admin/majors")
@RequiredArgsConstructor
@Tag(name = "Chuyên ngành", description = "API quản trị cho chuyên ngành")
public class MajorController {
    private final MajorRepository majorRepository;
    private final MajorMapper majorMapper;

    @PostMapping
    @Operation(summary = "Tạo chuyên ngành", description = "Thêm mới một chuyên ngành")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Tạo thành công", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MajorDto.class))),
            @ApiResponse(responseCode = "400", description = "Yêu cầu không hợp lệ", content = @Content)
    })
    public ResponseEntity<MajorDto> addMajor(@RequestBody MajorDto request) {
        System.out.println("Adding major: " + request);
        Major major = majorRepository.save(majorMapper.toEntity(request));
        MajorDto dto = majorMapper.toDto(major);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @GetMapping
    @Operation(summary = "Danh sách chuyên ngành", description = "Lấy danh sách tất cả chuyên ngành đang hoạt động")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Thành công", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = MajorDto.class))))
    })
    public ResponseEntity<List<MajorDto>> getAllMajors() {
        List<Major> majors = majorRepository.findAllActive();
        List<MajorDto> majorDtos = majors.stream().map(major -> majorMapper.toDto(major)).toList();
        return ResponseEntity.ok(majorDtos);
    }

    @GetMapping("/{majorCode}")
    @Operation(summary = "Chi tiết chuyên ngành", description = "Lấy thông tin chi tiết theo mã chuyên ngành")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Thành công", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MajorDto.class))),
            @ApiResponse(responseCode = "404", description = "Không tìm thấy", content = @Content)
    })
    public ResponseEntity<MajorDto> getMajorByCode(@PathVariable String majorCode) {
        System.out.println("Fetching major with code: " + majorCode);
        return majorRepository.findActiveById(majorCode)
                .map(major -> ResponseEntity.ok(majorMapper.toDto(major)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PutMapping("/{majorCode}")
    @Operation(summary = "Cập nhật chuyên ngành", description = "Cập nhật thông tin chuyên ngành theo mã")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cập nhật thành công", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MajorDto.class))),
            @ApiResponse(responseCode = "404", description = "Không tìm thấy", content = @Content),
            @ApiResponse(responseCode = "400", description = "Yêu cầu không hợp lệ", content = @Content)
    })
    public ResponseEntity<MajorDto> updateMajor(@PathVariable String majorCode, @RequestBody MajorDto request) {
        return majorRepository.findActiveById(majorCode)
                .map(existing -> {
                    Major major = majorMapper.toEntity(request);
                    major.setMajorCode(majorCode);
                    Major saved = majorRepository.save(major);
                    return ResponseEntity.ok(majorMapper.toDto(saved));
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @DeleteMapping("/{majorCode}")
    @Operation(summary = "Xóa chuyên ngành", description = "Xóa chuyên ngành theo mã")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Xóa thành công", content = @Content),
            @ApiResponse(responseCode = "404", description = "Không tìm thấy", content = @Content)
    })
    public ResponseEntity<Void> deleteMajor(@PathVariable String majorCode) {
        if (majorRepository.findActiveById(majorCode).isPresent()) {
            majorRepository.deleteById(majorCode);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

}

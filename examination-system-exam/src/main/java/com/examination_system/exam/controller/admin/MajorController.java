package com.examination_system.exam.controller.admin;

import java.util.List;

import com.examination_system.exam.model.dto.common.MajorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.examination_system.model.entity.exam.Major;
import com.examination_system.exam.model.mapper.MajorMapper;
import com.examination_system.repository.exam.MajorRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/admin/majors")
@RequiredArgsConstructor
public class MajorController {
    private final MajorRepository majorRepository;
    private final MajorMapper majorMapper;

    @PostMapping
    public ResponseEntity<MajorDto> addMajor(@RequestBody MajorDto request) {
        System.out.println("Adding major: " + request);
        Major major = majorRepository.save(majorMapper.toEntity(request));
        MajorDto dto = majorMapper.toDto(major);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @GetMapping
    public ResponseEntity<List<MajorDto>> getAllMajors() {
        List<Major> majors = majorRepository.findAllActive();
        List<MajorDto> majorDtos = majors.stream().map(major -> majorMapper.toDto(major)).toList();
        return ResponseEntity.ok(majorDtos);
    }

    @GetMapping("/{majorCode}")
    public ResponseEntity<MajorDto> getMajorByCode(@PathVariable String majorCode) {
        System.out.println("Fetching major with code: " + majorCode);
        return majorRepository.findActiveById(majorCode)
                .map(major -> ResponseEntity.ok(majorMapper.toDto(major)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PutMapping("/{majorCode}")
    public ResponseEntity<MajorDto> updateMajor(@PathVariable String majorCode, @RequestBody MajorDto request) {
        System.out.println("Updating major: " + request);
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
    public ResponseEntity<Void> deleteMajor(@PathVariable String majorCode) {
        if (majorRepository.findActiveById(majorCode).isPresent()) {
            majorRepository.deleteById(majorCode);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

}

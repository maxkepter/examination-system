package com.examination_system.exam.controller.admin;

import java.util.List;

import com.examination_system.exam.model.dto.common.MajorDto;
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
    public ResponseEntity<?> addMajor(@RequestBody MajorDto request) {
        System.out.println("Adding major: " + request);
      Major major=   majorRepository.save(majorMapper.toEntity(request));
        return  ResponseEntity.ok(majorMapper.toDto(major));
    }

    @GetMapping
    public List<MajorDto> getAllMajors() {
        List<Major> majors = majorRepository.findAllActive();
        List<MajorDto> majorDtos = majors.stream().map(major -> majorMapper.toDto(major)).toList();
        return majorDtos;
    }

    @GetMapping("/{majorCode}")
    public MajorDto getMajorByCode(@PathVariable String majorCode) {
        System.out.println("Fetching major with code: " + majorCode);
        Major major = majorRepository.findActiveById(majorCode)
                .orElseThrow(() -> new IllegalArgumentException("Major not found with code: " + majorCode));
        return majorMapper.toDto(major);
    }

    @PutMapping("/{majorCode}")
    public void updateMajor(@PathVariable String majorCode, @RequestBody MajorDto request) {
        System.out.println("Updating major: " + request);
        Major major = majorMapper.toEntity(request);
        major.setMajorCode(majorCode);
        majorRepository.findActiveById(majorCode)
                .orElseThrow(() -> new IllegalArgumentException("Major with code " + majorCode + " does not exist."));
        majorRepository.save(major);
    }

    @DeleteMapping("/{majorCode}")
    public void deleteMajor(@PathVariable String majorCode) {
        majorRepository.deleteById(majorCode);
    }

}

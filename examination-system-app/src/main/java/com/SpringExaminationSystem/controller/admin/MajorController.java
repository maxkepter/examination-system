package com.SpringExaminationSystem.controller.admin;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.SpringExaminationSystem.model.dto.common.MajorDTO;
import com.SpringExaminationSystem.model.entity.exam.Major;
import com.SpringExaminationSystem.model.mapper.exam.MajorMapper;
import com.SpringExaminationSystem.repository.exam.MajorDao;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin/majors")
@RequiredArgsConstructor
public class MajorController {
    // This controller will handle requests related to majors in the examination
    // system.
    // You can define methods here to handle various endpoints related to majors.

    private final MajorDao majorDao;
    private final MajorMapper majorMapper;

    @PostMapping
    public void addMajor(@RequestBody MajorDTO request) {
        System.out.println("Adding major: " + request);
        majorDao.save(majorMapper.toEntity(request));
    }

    @GetMapping
    public List<MajorDTO> getAllMajors() {
        List<Major> majors = majorDao.findAllActive();
        List<MajorDTO> majorDTOs = majors.stream().map(major -> majorMapper.toDto(major)).toList();
        return majorDTOs;
    }

    @GetMapping("/{majorCode}")
    public MajorDTO getMajorByCode(@PathVariable String majorCode) {
        System.out.println("Fetching major with code: " + majorCode);
        Major major = majorDao.findActiveById(majorCode)
                .orElseThrow(() -> new IllegalArgumentException("Major not found with code: " + majorCode));
        return majorMapper.toDto(major);
    }

    @PutMapping("/{majorCode}")
    public void updateMajor(@PathVariable String majorCode, @RequestBody MajorDTO request) {
        System.out.println("Updating major: " + request);
        Major major = majorMapper.toEntity(request);
        major.setMajorCode(majorCode);
        majorDao.findActiveById(majorCode)
                .orElseThrow(() -> new IllegalArgumentException("Major with code " + majorCode + " does not exist."));
        majorDao.save(major);
    }

    @DeleteMapping("/{majorCode}")
    public void deleteMajor(@PathVariable String majorCode) {
        System.out.println(majorCode);
        majorDao.deleteById(majorCode);
    }

}

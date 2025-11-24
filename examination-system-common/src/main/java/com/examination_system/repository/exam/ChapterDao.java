package com.examination_system.repository.exam;

import org.springframework.stereotype.Repository;

import com.examination_system.model.entity.exam.Chapter;
import com.examination_system.repository.SoftDeleteRepository;

@Repository
public interface ChapterDao extends SoftDeleteRepository<Chapter, Integer> {

}

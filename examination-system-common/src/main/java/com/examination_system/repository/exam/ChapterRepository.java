package com.examination_system.repository.exam;

import org.springframework.stereotype.Repository;

import com.examination_system.core.repository.SoftDeleteRepository;
import com.examination_system.model.entity.exam.Chapter;

@Repository
public interface ChapterRepository extends SoftDeleteRepository<Chapter, Integer> {

}

package com.examination_system.repository.exam;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.examination_system.core.repository.SoftDeleteRepository;
import com.examination_system.model.entity.exam.Subject;

import java.util.List;

@Repository
public interface SubjectRepository extends SoftDeleteRepository<Subject, String> {
    @Modifying
    @Query("SELECT s FROM Subject s JOIN s.majors m WHERE m.majorCode = ?1")
    public List<Subject> findByMajorCode(String majorCode);
    @Override
    @EntityGraph(attributePaths = {"majors"})
    List<Subject> findAll();
}

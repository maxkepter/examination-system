package com.examination_system.common.repository.exam;

import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import com.examination_system.core.repository.SoftDeleteRepository;
import com.examination_system.common.model.entity.exam.Subject;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubjectRepository extends SoftDeleteRepository<Subject, String> {
    @Query("SELECT DISTINCT s FROM Subject s " +
            "LEFT JOIN FETCH s.majors m " +
            "LEFT JOIN FETCH s.chapters c " +
            "WHERE m.majorCode = ?1 AND s.isActive = true AND c.isActive = true")
    List<Subject> findByMajorCode(String majorCode);

    @NonNull
    @Override
    @Query("SELECT DISTINCT s FROM Subject s " +
            "LEFT JOIN FETCH s.majors " +
            "LEFT JOIN FETCH s.chapters c " +
            "WHERE s.isActive = true AND c.isActive = true")
    List<Subject> findAll();

    @Override
    @Query("SELECT s FROM Subject s " +
            "LEFT JOIN FETCH s.chapters c " +
            "WHERE s.subjectCode = ?1 AND s.isActive = true AND c.isActive = true")
    Optional<Subject> findActiveById(String s);
}

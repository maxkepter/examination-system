package com.examination_system.model.entity.exam;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.ManyToAny;
import org.hibernate.annotations.SQLDelete;
import org.springframework.context.annotation.Scope;

import com.examination_system.core.model.entity.BaseEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)

@Entity
@Table(name = "Subject")
@SQLDelete(sql = "update Subject set isActive=0 where subId=?")
public class Subject extends BaseEntity {
    @Id
    private String subjectCode;

    @Column(nullable = false, length = 100)
    private String subjectName;

    @ManyToMany
    @JoinTable(name = "subject_major", joinColumns = @JoinColumn(name = "subjectCode"), inverseJoinColumns = @JoinColumn(name = "majorCode"))
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Major> majors;

    @OneToMany(mappedBy = "subject", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Chapter> chapters;

}
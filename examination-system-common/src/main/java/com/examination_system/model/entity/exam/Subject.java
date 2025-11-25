package com.examination_system.model.entity.exam;

import java.util.List;

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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@Scope("prototype")
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
    private List<Major> majors;

    @OneToMany(mappedBy = "subject", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Chapter> chapters;

}
package com.examination_system.common.model.entity.exam;

import org.hibernate.annotations.SQLDelete;
import org.springframework.context.annotation.Scope;

import com.examination_system.core.model.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
@Table(name = "Major")
@SQLDelete(sql = "update Major set isActive=0 where majorCode=?")
public class Major extends BaseEntity {
    @Id
    private String majorCode;

    @Column(nullable = false, length = 100)
    private String majorName;

}
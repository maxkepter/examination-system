package com.examination_system.core.model.entity;

import java.util.Date;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@MappedSuperclass
@NoArgsConstructor
@Data
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public abstract class BaseLog {

    public BaseLog(String infomation) {
        this.infomation = infomation;
    }

    public static final String FIELD_INFOMATION = "infomation";
    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false)
    private Date createdAt;
    @CreatedBy
    @Column(updatable = false)
    private String createdBy;
    @Column(updatable = false, length = 100)
    private String infomation;
}

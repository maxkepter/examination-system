package com.examination_system.model.entity.log;

import java.time.LocalDateTime;

import org.springframework.context.annotation.Scope;

import com.examination_system.core.model.entity.BaseLog;
import com.examination_system.model.entity.user.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Scope("prototype")
@Entity
public class BanLog extends BaseLog {
    public static final String FIELD_USER = "user";
    public static final String FIELD_END_TIME = "endTime";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long logId;

    @Column(nullable = false, columnDefinition = "DATETIME")
    private LocalDateTime endTime;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

}
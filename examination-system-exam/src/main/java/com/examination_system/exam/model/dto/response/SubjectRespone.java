package com.examination_system.exam.model.dto.response;

import com.examination_system.exam.model.dto.common.ChapterDto;
import com.examination_system.exam.model.dto.common.MajorDto;
import com.examination_system.exam.model.dto.common.SubjectDto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class SubjectRespone extends SubjectDto {
    List<ChapterDto> chapters;
    List<MajorDto> majors;
}

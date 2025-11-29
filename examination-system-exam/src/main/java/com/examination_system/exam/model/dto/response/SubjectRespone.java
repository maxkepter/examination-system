package com.examination_system.exam.model.dto.response;

import com.examination_system.exam.model.dto.common.ChapterDto;
import com.examination_system.exam.model.dto.common.MajorDto;
import com.examination_system.exam.model.dto.common.SubjectDto;

import java.util.List;

public class SubjectResponeDto extends SubjectDto {
    List<ChapterDto> chapters;
    List<MajorDto> majors;
}

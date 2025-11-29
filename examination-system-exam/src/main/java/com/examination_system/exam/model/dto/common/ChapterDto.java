package com.examination_system.exam.model.dto.common;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Length;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChapterDto {
    @NotEmpty
    Long chapterId;
    @NotBlank
    @Length(max=100)
    String chapterName;
}

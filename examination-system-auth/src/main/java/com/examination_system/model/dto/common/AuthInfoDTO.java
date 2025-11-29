package com.examination_system.model.dto.common;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Value;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthInfoDTO {
    @NotEmpty
    Long userId;
    @NotBlank
    @Length(max = 255)
    String userName;
    @NotBlank
    @Size(min = 6, max = 100)
    String password;
    @Value("1")
    @NotNull
    Integer role;
}

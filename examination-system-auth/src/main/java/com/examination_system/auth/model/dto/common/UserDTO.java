package com.examination_system.auth.model.dto.common;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class UserDTO {

    @NotNull
    @Size(max = 100)
    String firstName;

    @NotNull
    @Size(max = 100)
    String lastName;

    @NotNull
    @Email
    @Size(max = 255)
    String email;

    @NotNull
    Long userId;
}

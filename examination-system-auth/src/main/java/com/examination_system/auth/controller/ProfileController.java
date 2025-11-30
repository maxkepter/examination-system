package com.examination_system.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.examination_system.auth.model.dto.request.UpdateProfileRequest;
import com.examination_system.auth.model.dto.response.ProfileResponse;
import com.examination_system.auth.service.UserService;
import com.examination_system.common.model.entity.user.User;
import com.examination_system.common.model.entity.user.UserPrincipal;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

/**
 * Profile Controller - quản lý thông tin cá nhân người dùng
 */
@RestController
@RequestMapping("/profile")
@Tag(name = "Hồ sơ cá nhân", description = "API quản lý thông tin cá nhân người dùng")
public class ProfileController {

    @Autowired
    private UserService userService;

    /**
     * Lấy thông tin chi tiết của người dùng
     * Chỉ cho phép xem thông tin của chính mình
     */
    @GetMapping("/{userId}")
    @Operation(summary = "Xem thông tin cá nhân", description = "Lấy thông tin chi tiết của người dùng (chỉ được xem thông tin của chính mình)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Thành công",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProfileResponse.class))),
            @ApiResponse(responseCode = "403", description = "Không có quyền truy cập - Chỉ được xem thông tin của chính mình",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Không tìm thấy người dùng",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "Chưa xác thực",
                    content = @Content)
    })
    public ResponseEntity<?> getUserProfile(@PathVariable Long userId) {
        try {
            // Lấy thông tin người dùng hiện tại từ SecurityContext
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            
            if (authentication == null || !authentication.isAuthenticated()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("User is not authenticated");
            }

            UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
            Long currentUserId = userPrincipal.getUserId();

            // Kiểm tra xem người dùng có đang cố truy cập thông tin của người khác không
            if (!currentUserId.equals(userId)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("Access denied. You can only view your own profile");
            }

            // Lấy thông tin user từ database
            User user = userService.getUserById(userId);

            // Tạo response
            ProfileResponse response = ProfileResponse.builder()
                    .userId(user.getUserId())
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .email(user.getEmail())
                    .build();

            return ResponseEntity.ok(response);

        } catch (RuntimeException ex) {
            String msg = ex.getMessage() != null ? ex.getMessage().toLowerCase() : "";
            if (msg.contains("not found")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("User not found");
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error retrieving user profile: " + ex.getMessage());
        }
    }

    /**
     * Cập nhật thông tin cá nhân của người dùng
     * Chỉ cho phép cập nhật thông tin của chính mình
     */
    @PutMapping("/{userId}")
    @Operation(summary = "Cập nhật thông tin cá nhân", description = "Cập nhật FirstName, LastName, Email (chỉ được cập nhật thông tin của chính mình)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cập nhật thành công",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "Không có quyền truy cập - Chỉ được cập nhật thông tin của chính mình",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Không tìm thấy người dùng",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Dữ liệu không hợp lệ",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "Chưa xác thực",
                    content = @Content)
    })
    public ResponseEntity<?> updateUserProfile(
            @PathVariable Long userId,
            @Valid @RequestBody UpdateProfileRequest request) {
        try {
            // Lấy thông tin người dùng hiện tại từ SecurityContext
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            
            if (authentication == null || !authentication.isAuthenticated()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("User is not authenticated");
            }

            UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
            Long currentUserId = userPrincipal.getUserId();

            // Kiểm tra xem người dùng có đang cố cập nhật thông tin của người khác không
            if (!currentUserId.equals(userId)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("Access denied. You can only update your own profile");
            }

            // Cập nhật thông tin user
            userService.updateProfile(
                    userId,
                    request.getFirstName(),
                    request.getLastName(),
                    request.getEmail()
            );

            return ResponseEntity.ok("Profile updated successfully");

        } catch (RuntimeException ex) {
            String msg = ex.getMessage() != null ? ex.getMessage().toLowerCase() : "";
            if (msg.contains("not found")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("User not found");
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error updating user profile: " + ex.getMessage());
        }
    }

}

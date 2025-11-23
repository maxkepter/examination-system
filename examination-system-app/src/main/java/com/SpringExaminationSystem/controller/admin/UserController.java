package com.SpringExaminationSystem.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.SpringExaminationSystem.model.dto.common.AuthInfoDTO;
import com.SpringExaminationSystem.model.dto.common.UserDTO;
import com.SpringExaminationSystem.service.exam.UserService;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/admin/user")
public class UserController {

    @Autowired
    UserService userService;

    @DeleteMapping("/{userId}")
    public String deleteUser(@PathVariable Integer userId) {
        userService.deleteUser(userId);
        return "Delete user successfully";
    }

    @PutMapping("/role")
    public String editUserRole(@RequestBody AuthInfoDTO authInfoDTO) {
        Integer userId = authInfoDTO.getUserId();
        Integer role = authInfoDTO.getRole();
        userService.editUserRole(userId, role);
        return "Update user role successfully";
    }

    @PutMapping()
    public String editUser(@RequestBody UserDTO userDTO) {
        userService.editUser(userDTO);

        return "Update user successfully";
    }

}

package com.furbaby.furbaby.controller;

import com.furbaby.furbaby.dto.LoginDTO;
import com.furbaby.furbaby.dto.RegisterDTO;
import com.furbaby.furbaby.dto.UserUpdateDTO;
import com.furbaby.furbaby.entity.Result;
import com.furbaby.furbaby.service.IUserService;
import com.furbaby.furbaby.utils.JWTUtils;
import com.furbaby.furbaby.vo.LoginVO;
import com.furbaby.furbaby.vo.RegisterVO;
import com.furbaby.furbaby.vo.UserInfoVO;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Tag(name = "用户管理")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final IUserService userService;
    private final JWTUtils jwtUtils;

    @Operation(summary = "用户登录", description = "通过手机号和密码进行登录")
    @PostMapping("/login")
    public Result<LoginVO> login(@RequestBody LoginDTO loginDTO) {
        return userService.login(loginDTO);
    }

    @PostMapping("/register")
    public  Result<RegisterVO> register(@RequestBody RegisterDTO registerDTO) {
        RegisterVO registerVO = userService.register(registerDTO);
        return Result.success(registerVO);
    }

    @Operation(summary = "获取当前用户信息", description = "通过JWT令牌获取当前登录用户的信息")
    @GetMapping("/info")
    public Result<UserInfoVO> getCurrentUserInfo(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        UserInfoVO userInfo = userService.getCurrentUserInfo(token);
        return Result.success(userInfo);
    }

    @Operation(summary = "更新当前用户信息", description = "更新当前登录用户的昵称、头像、邮箱")
    @PutMapping("/info")
    public Result<UserInfoVO> updateCurrentUserInfo(@RequestHeader("Authorization") String authHeader,
                                                     @RequestBody UserUpdateDTO updateDTO) {
        String token = authHeader.replace("Bearer ", "");
        UserInfoVO userInfo = userService.updateCurrentUserInfo(token, updateDTO);
        return Result.success(userInfo);
    }

    @Operation(summary = "获取指定用户信息", description = "根据用户ID获取用户公开信息")
    @GetMapping("/info/{userId}")
    public Result<UserInfoVO> getUserInfoById(@PathVariable Long userId) {
        UserInfoVO userInfo = userService.getUserInfoById(userId);
        return Result.success(userInfo);
    }

}


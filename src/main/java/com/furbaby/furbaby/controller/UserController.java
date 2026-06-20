package com.furbaby.furbaby.controller;

import com.furbaby.furbaby.dto.LoginDTO;
import com.furbaby.furbaby.dto.RegisterDTO;
import com.furbaby.furbaby.entity.Result;
import com.furbaby.furbaby.service.IUserService;
import com.furbaby.furbaby.vo.LoginVO;
import com.furbaby.furbaby.vo.RegisterVO;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Tag(name = "用户管理")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final IUserService userService;

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

}


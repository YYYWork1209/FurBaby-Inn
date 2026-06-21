package com.furbaby.furbaby.service;

import com.furbaby.furbaby.dto.LoginDTO;
import com.furbaby.furbaby.dto.RegisterDTO;
import com.furbaby.furbaby.dto.UserUpdateDTO;
import com.furbaby.furbaby.entity.Result;
import com.furbaby.furbaby.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.furbaby.furbaby.vo.LoginVO;
import com.furbaby.furbaby.vo.RegisterVO;
import com.furbaby.furbaby.vo.UserInfoVO;

import java.util.Map;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author YYYWork1209
 * @since 2026-06-19
 */
public interface IUserService extends IService<User> {

    Result<LoginVO> login(LoginDTO loginDTO);

    RegisterVO register(RegisterDTO registerDTO);

    UserInfoVO getCurrentUserInfo(String token);

    UserInfoVO updateCurrentUserInfo(String token, UserUpdateDTO updateDTO);

    UserInfoVO getUserInfoById(Long userId);

    Map<String, String> logout(String token);
}

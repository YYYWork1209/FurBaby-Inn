package com.furbaby.furbaby.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.furbaby.furbaby.dto.LoginDTO;
import com.furbaby.furbaby.dto.RegisterDTO;
import com.furbaby.furbaby.dto.UserUpdateDTO;
import com.furbaby.furbaby.entity.Result;
import com.furbaby.furbaby.entity.Shop;
import com.furbaby.furbaby.entity.User;
import com.furbaby.furbaby.exception.NoRegisterException;
import com.furbaby.furbaby.exception.PhoneOrPasswordException;
import com.furbaby.furbaby.exception.UnAuthorizedException;
import com.furbaby.furbaby.mapper.UserMapper;
import com.furbaby.furbaby.security.TokenBlacklist;
import com.furbaby.furbaby.service.IUserService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.furbaby.furbaby.utils.JWTUtils;
import com.furbaby.furbaby.vo.LoginVO;
import com.furbaby.furbaby.vo.RegisterVO;
import com.furbaby.furbaby.vo.UserInfoVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author YYYWork1209
 * @since 2026-06-19
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    private final JWTUtils jwtUtils;
    private final TokenBlacklist tokenBlacklist;

    @Override
    public Result<LoginVO> login(LoginDTO loginDTO) {
        // 校验手机号与对应用户的密码是否正确
        // 如果正确,则返回登录成功
        User currentLoginUser = lambdaQuery()
                .eq(User::getPhone, loginDTO.getPhone())
                .one();
        // 校验手机号是否存在
        if (currentLoginUser == null) {
            throw new NoRegisterException("手机号不存在,请先注册");
        }

        if (currentLoginUser.getPassword().equals(loginDTO.getPassword())) {
            // 登录成功,把用户ID封装到JWT中,并返回给前端
            String token = jwtUtils.generateToken(currentLoginUser.getId().toString());
            UserInfoVO userInfoVO = new UserInfoVO();
            BeanUtils.copyProperties(currentLoginUser, userInfoVO);
            // BeanUtils 无法将枚举自动转为 String，手动设置 role
            userInfoVO.setRole(currentLoginUser.getRole().name());
            LoginVO loginVO = LoginVO.builder().token(token).userInfo(userInfoVO).build();
            return Result.success(loginVO);

        } else {
            // 登录失败
            throw new PhoneOrPasswordException("手机号或密码错误");
        }

    }

    /**
     * 用户注册,可能是顾客也可能是商家
     * @param registerDTO 注册信息
     * @return 注册成功后的用户信息
     */
    @Override
    public RegisterVO register(RegisterDTO registerDTO) {
        // 校验手机号是否已注册
        User currentRegisterUser = lambdaQuery()
                .eq(User::getPhone, registerDTO.getPhone())
                .one();

        if (currentRegisterUser != null) {
            throw new PhoneOrPasswordException("手机号已注册");
        }
        // 无论是顾客还是商家都属于该平台的用户，信息都会保存到用户表中
        User newUser = new User();
        RegisterVO registerVO = new RegisterVO();

        // 是新用户，判断是顾客还是商家注册
        if (registerDTO.getRole().equals("shop")) {
            // 商家注册
            BeanUtils.copyProperties(registerDTO, newUser);
            // 保存商家用户
            save(newUser);
            // 注册成功,返回注册成功后的用户信息
            BeanUtils.copyProperties(newUser, registerVO);
        } else {
            // 顾客注册
            BeanUtils.copyProperties(registerDTO, newUser);
            // 保存顾客用户
            save(newUser);
            // 注册成功,返回注册成功后的用户信息
            BeanUtils.copyProperties(newUser, registerVO);
        }

        return registerVO;
    }

    @Override
    public UserInfoVO getCurrentUserInfo(String token) {
        Long userId = Long.valueOf(jwtUtils.getUserIdFromToken(token));
        User user = this.getOne(Wrappers.<User>lambdaQuery().eq(User::getId, userId));
        if (user == null) {
            throw new NoRegisterException("用户不存在");
        }
        UserInfoVO vo = new UserInfoVO();
        BeanUtils.copyProperties(user, vo);
        return vo;
    }

    @Override
    public UserInfoVO updateCurrentUserInfo(String token, UserUpdateDTO updateDTO) {
        Long userId = Long.valueOf(jwtUtils.getUserIdFromToken(token));
        User user = this.getOne(Wrappers.<User>lambdaQuery().eq(User::getId, userId));
        if (user == null) {
            throw new NoRegisterException("用户不存在");
        }
        if (updateDTO.getNickname() != null) {
            user.setNickname(updateDTO.getNickname());
        }
        if (updateDTO.getAvatar() != null) {
            user.setAvatar(updateDTO.getAvatar());
        }
        if (updateDTO.getEmail() != null) {
            user.setEmail(updateDTO.getEmail());
        }
        user.setUpdateTime(LocalDateTime.now());
        this.updateById(user);

        UserInfoVO vo = new UserInfoVO();
        BeanUtils.copyProperties(user, vo);
        return vo;
    }

    @Override
    public UserInfoVO getUserInfoById(Long userId) {
        User user = this.getOne(Wrappers.<User>lambdaQuery().eq(User::getId, userId));
        if (user == null) {
            throw new NoRegisterException("用户不存在");
        }
        UserInfoVO vo = new UserInfoVO();
        BeanUtils.copyProperties(user, vo);
        return vo;
    }

    @Override
    public Map<String, String> logout(String token) {
        tokenBlacklist.blacklist(token);
        return Map.of("success", "true", "message", "已退出登录");
    }
}

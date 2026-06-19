package com.furbaby.furbaby.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.furbaby.furbaby.dto.LoginDTO;
import com.furbaby.furbaby.entity.User;
import com.furbaby.furbaby.exception.NoRegisterException;
import com.furbaby.furbaby.exception.PhoneOrPasswordException;
import com.furbaby.furbaby.exception.UnAuthorizedException;
import com.furbaby.furbaby.mapper.UserMapper;
import com.furbaby.furbaby.service.IUserService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.furbaby.furbaby.utils.JWTUtils;
import com.furbaby.furbaby.vo.LoginVO;
import com.furbaby.furbaby.vo.UserInfoVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

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

    @Override
    public LoginVO login(LoginDTO loginDTO) {
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
            return LoginVO.builder().token(token).userInfo(userInfoVO).build();

        } else {
            // 登录失败
            throw new PhoneOrPasswordException("手机号或密码错误");
        }

    }
}

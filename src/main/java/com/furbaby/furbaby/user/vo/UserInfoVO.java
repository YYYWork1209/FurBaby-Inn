package com.furbaby.furbaby.user.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoVO {

    private Long id;
    private String nickname;
    private String avatar;
    private String role;
    private String phone;
    private String email;
    private LocalDateTime createTime;
}

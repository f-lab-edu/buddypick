package com.buddypick.user.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MemberDto {

    private Long id;

    private String email;

    private String password;

    private String nickName;

    private String phoneNumber;

    private String role;
}

package com.example.memberservice.dto;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberRequestDto {
    private String nickname;
    private String email;
    private String password;
}

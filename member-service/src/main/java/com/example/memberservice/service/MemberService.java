package com.example.memberservice.service;

import com.example.memberservice.domain.Member;
import com.example.memberservice.dto.MemberRequestDto;
import com.example.memberservice.dto.MemberResponseDto;
import com.example.memberservice.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public MemberResponseDto createMember(MemberRequestDto request) {
        if(memberRepository.existsByNickname(request.getNickname())){
            throw new IllegalArgumentException("이미존재하는 닉네임입니다");
        }

        if (memberRepository.existsByEmail((request.getEmail()))) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다");
        }

        Member member = Member.builder()
                .nickname(request.getNickname())
                .email(request.getEmail())
                .passsword(request.getPassword())
                .build();

        Member saved = memberRepository.save(member);
        return MemberResponseDto.from(saved);
    }

    public MemberResponseDto getMember(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("회원을 찾을수 없습니다."));
        return MemberResponseDto.from(member);
    }

    public MemberResponseDto getMemberByNickname(String nickname) {
        Member member = memberRepository.findByNickname(nickname)
                .orElseThrow(() -> new IllegalArgumentException("회원을 찾을수 없습니다"));

        return MemberResponseDto.from(member);
    }
}

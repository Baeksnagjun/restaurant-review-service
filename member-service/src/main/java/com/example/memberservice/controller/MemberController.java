package com.example.memberservice.controller;


import com.example.memberservice.domain.Member;
import com.example.memberservice.dto.MemberRequestDto;
import com.example.memberservice.dto.MemberResponseDto;
import com.example.memberservice.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<MemberResponseDto> createMember(@RequestBody MemberRequestDto request){
        MemberResponseDto response = memberService.createMember(request);
        return ResponseEntity
                .status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemberResponseDto> getMember(@PathVariable Long id) {
        MemberResponseDto response = memberService.getMember(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<MemberResponseDto> getMemberByNickname(@RequestParam String nickname) {
        MemberResponseDto respone = memberService.getMemberByNickname(nickname);
        return ResponseEntity.ok(respone);
    }

}

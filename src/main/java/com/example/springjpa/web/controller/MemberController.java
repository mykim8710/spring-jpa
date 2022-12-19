package com.example.springjpa.web.controller;

import com.example.springjpa.domain.Address;
import com.example.springjpa.domain.Member;
import com.example.springjpa.service.MemberService;
import com.example.springjpa.web.dto.MemberFormDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Slf4j
@RequiredArgsConstructor
@Controller
public class MemberController {
    private final MemberService memberService;

    // 회원가입 View
    @GetMapping("/members/join")
    public String memberJoinFormView(Model model) {
        log.info("[GET] /members/join  => Member Join View");
        model.addAttribute("memberFormDto", new MemberFormDto());
        return "members/memberJoinForm";
    }

    // 회원가입
    @PostMapping("/members/join")
    public String memberJoin(@Valid MemberFormDto dto, BindingResult bindingResult) {
        log.info("[POST] /members/join  => Member Join");
        log.info("MemberFormDto = {}", dto);
        log.info("BindingResult = {}", bindingResult);

        if(bindingResult.hasErrors()) {
            return "members/memberJoinForm";
        }

        Member member = new Member();
        member.setName(dto.getName());
        member.setAddress(new Address(dto.getCity(), dto.getStreet(), dto.getZipcode()));

        memberService.joinMember(member);

        return "redirect:/members";
    }

    // 회원목록
    @GetMapping("/members")
    public String memberListView(Model model) {
        log.info("[GET] /members  => Member List View");
        model.addAttribute("members", memberService.findMembers());
        return "members/memberList";
    }
}

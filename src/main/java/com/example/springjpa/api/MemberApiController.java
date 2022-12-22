package com.example.springjpa.api;

import com.example.springjpa.api.dto.member.request.RequestMemberUpdateDto;
import com.example.springjpa.api.dto.member.request.RequestMemberInsertDto;
import com.example.springjpa.api.dto.member.response.ResponseMemberSelectDto;
import com.example.springjpa.api.dto.member.response.ResponseMemberUpdateDto;
import com.example.springjpa.api.dto.member.response.ResponseMemberInsertDto;
import com.example.springjpa.global.result.CommonResult;
import com.example.springjpa.domain.Address;
import com.example.springjpa.domain.Member;
import com.example.springjpa.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@RestController
public class MemberApiController {
    private final MemberService memberService;

    /**
     * 회원조회(전체) api V1 : 응답 값으로 엔티티를 직접 외부에 노출한다.
     */
    @GetMapping("/api/v1/members")
    public List<Member> membersV1() {
        log.info("[GET] /api/v1/members  =>  get member List API Version1");
        return memberService.findMembers();
    }

    /**
     * 회원등록 api V1 : 요청 값으로 Member 엔티티를 직접 받는다.
     */
    @PostMapping("/api/v1/members")
    public ResponseMemberInsertDto joinMemberV1(@RequestBody @Valid Member member) {
        log.info("[POST] /api/v1/members  =>  member Join API Version1");
        Long memberId = memberService.joinMember(member);
        return new ResponseMemberInsertDto(memberId);
    }

    /**
     * 회원조회(전체) api V2 : 응답 값으로 엔티티가 아닌 별도의 DTO 사용
     */
    @GetMapping("/api/v2/members")
    public CommonResult membersV2() {
        log.info("[GET] /api/v2/members  =>  get member List API Version2");

        List<Member> findMembers = memberService.findMembers();

        List<ResponseMemberSelectDto> responseMemberSelectDtos = new ArrayList<>();
        if(findMembers.size() > 0) {
            responseMemberSelectDtos.addAll(
                                findMembers
                                        .stream()
                                        .map(findMember -> ResponseMemberSelectDto.builder()
                                                                                            .id(findMember.getId())
                                                                                            .name(findMember.getName())
                                                                                            .city(findMember.getAddress().getCity())
                                                                                            .street(findMember.getAddress().getStreet())
                                                                                            .zipcode(findMember.getAddress().getZipcode())
                                                                                            .build())
                                        .collect(Collectors.toList())
            );
        }

        return new CommonResult<>( responseMemberSelectDtos);
    }


    /**
     * 회원등록 api V2: 요청 값으로 Member 엔티티 대신에 별도의 DTO를 받는다.
     */
    @PostMapping("/api/v2/members")
    public ResponseMemberInsertDto joinMemberV2(@RequestBody @Valid RequestMemberInsertDto dto) {
        log.info("[POST] /api/v2/members  =>  member Join API Version2");
        log.info("RequestMemberInsertDto = {}", dto);

        Member member = new Member();
        member.setName(dto.getName());
        member.setAddress(new Address(dto.getCity(), dto.getStreet(), dto.getZipcode()));
        Long memberId = memberService.joinMember(member);
        return new ResponseMemberInsertDto(memberId);
    }

    /**
     * 회원수정 api V2
     */
    @PutMapping("/api/v2/members/{memberId}")
    private ResponseMemberUpdateDto editMember(@PathVariable Long memberId, @RequestBody RequestMemberUpdateDto dto) {
        log.info("[POST] /api/v2/members/{}  =>  member Edit API", memberId);
        log.info("RequestMemberUpdateDto = {}", dto);

        // 회원수정
        memberService.editMember(memberId, dto.getName(), dto.getCity(), dto.getStreet(), dto.getZipcode());

        // command(edit) 와 query 를 분리
        Member findMember = memberService.findMemberOne(memberId);

        return new ResponseMemberUpdateDto(findMember.getId(), findMember.getName());
    }
}

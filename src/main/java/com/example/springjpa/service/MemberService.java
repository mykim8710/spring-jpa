package com.example.springjpa.service;

import com.example.springjpa.domain.Address;
import com.example.springjpa.domain.Member;
import com.example.springjpa.repository.MemberRepository;
import com.example.springjpa.repository.MemberRepositoryOld;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
@Transactional(readOnly = true)
// jpa의 로직(insert, update, delete)들은 트랜젝션안에서 실행되어야 한다.
// class 영역에 설정하면 public method에 적용이 됨
// readOnly = true -> jpa가 조회하는 곳에서는 성능을 최적화함
// ㄴ 데이터의 변경이 없는 읽기 전용 메서드에 사용, 영속성 컨텍스트를 플러시 하지 않 으므로 약간의 성능 향상(읽기 전용에는 다 적용)
// ㄴ 데이터베이스 드라이버가 지원하면 DB에서 성능 향상
public class MemberService {
    private final MemberRepository memberRepository;

    /**
    * 회원가입 : joinMember
    */
    @Transactional // readOny = false
    public Long joinMember(Member member) {
        validateDuplicateMember(member.getName()); // 회원 중복확인 validation
        memberRepository.save(member);
        return member.getId();
    }

    /**
     * 전체 회원조회 : findMembers
     */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    /**
     * 회원 단건 조회 : findMemberOne by memberId
     */
    public Member findMemberOne(Long memberId) {
        return memberRepository.findById(memberId)
                    .orElseThrow(() -> new IllegalArgumentException("해당 회원을 찾을 수 없습니다."));
    }

    /**
     * 회원수정 : 변경감지(Dirty Checking)
     */
    @Transactional
    public void editMember(Long memberId, String name, String city, String street, String zipcode) {
        Member findMember = memberRepository.findById(memberId).orElseThrow(() -> new IllegalArgumentException("해당 회원을 찾을 수 없습니다."));
        findMember.setName(name);
        findMember.setAddress(new Address(city, street, zipcode));
    }

    private void validateDuplicateMember(String name) {
        List<Member> findMembers = memberRepository.findAllByName(name); // name을 db에서 유니크 제약조건 설정 권장
        if(!findMembers.isEmpty()) {
            throw new IllegalArgumentException("이미 존재하는 회원입니다.");
        }
    }
}

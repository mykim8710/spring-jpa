package com.example.springjpa.service;

import com.example.springjpa.domain.Member;
import com.example.springjpa.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest // 스프링을 사용하여 테스트 진행, @ExtendWith(SpringExtension.class) 포함
// 이게 없으면 @Autowired, 빈주입이 안된다
class MemberServiceTest {
    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @Test
    @DisplayName("회원가입 테스트")
    @Transactional // rollback after test
    void 회원가입_테스트() throws Exception {
        // given
        Member member = new Member();
        member.setName("mykim");

        // when
        Long joinMemberId = memberService.joinMember(member);

        // then
        assertThat(member).isEqualTo(memberRepository.findById(joinMemberId).get()); // jpa 엔티티 영속
    }

    @Test
    @DisplayName("회원가입 시 중복체크 예외 테스트")
    @Transactional  // rollback after test
    void 회원가입_중복확인_예외_테스트() throws Exception {
        // given
        Member member1 = new Member();
        member1.setName("test");

        Member member2 = new Member();
        member2.setName("test");

        // when
        memberService.joinMember(member1);

        // then : IllegalArgumentException 발생
        assertThrows(IllegalArgumentException.class, () -> {
            memberService.joinMember(member2);
        });
    }
}
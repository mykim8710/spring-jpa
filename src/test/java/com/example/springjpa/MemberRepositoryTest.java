package com.example.springjpa;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class MemberRepositoryTest {
    @Autowired
    MemberRepository memberRepository;

    @Test
    @DisplayName("JPA save, find - DB 연동 Test")
    @Transactional // jpa의 변화(insert, update, delete)는 트랙잭션 안에서 이루어져야한다.
    // @Rollback(value = false) // 롤백 X
    void jpaDBSettingTest() {
        // given
        Member member = new Member();
        member.setName("new Member");

        // when
        Long saveMemberId = memberRepository.save(member);

        // then
        Member findMember = memberRepository.find(saveMemberId);

        assertThat(findMember.getId()).isEqualTo(member.getId());
        assertThat(findMember.getName()).isEqualTo(member.getName());
        assertThat(findMember).isEqualTo(member);   // jpa의 특징, 엔티티 동일성 보장 : 같은 트랙젝션, 영속성 컨텍스트안에서는 식별자가 같은면 같은 엔티티
        System.out.println(findMember == member);
    }

}


package com.example.springjpa;

import com.example.springjpa.member.Member;
import com.example.springjpa.member.MemberRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MemberRepositoryTest {
    @Autowired
    MemberRepository memberRepository;

    @Test
    @Transactional  // jpa의 변화(insert, update, delete)는 트랙잭션 안에서 이루어져야한다.'
    @Rollback(false)
    public void saveTest() {
        // given
        Member member = new Member("memberA");

        // when
        Long savedMemberId = memberRepository.save(member);

        // then
        Member findMember = memberRepository.findById(savedMemberId);
        assertThat(findMember.getId()).isEqualTo(savedMemberId);
        assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
        assertThat(findMember).isEqualTo(member);    // == jpa 엔티티 동일성 보장
    }

}
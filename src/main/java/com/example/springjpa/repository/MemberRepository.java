package com.example.springjpa.repository;

import com.example.springjpa.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {
    // select m from Member m where m.name = :name => jpql
    List<Member> findAllByName(String name);
}
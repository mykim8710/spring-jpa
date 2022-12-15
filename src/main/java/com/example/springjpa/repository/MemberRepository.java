package com.example.springjpa.repository;

import com.example.springjpa.domain.Member;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class MemberRepository {
    @PersistenceContext // JPA 스펙에서 제공하는 기능, 영속성 컨텍스트를 주입하는 표준 애노테이션
    private EntityManager em;

    // 회원 등록
    public void save(Member member) {
        em.persist(member);
    }

    // 회원 단건 조회 by id
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(em.find(Member.class, id));
    }

    // 회원리스트 조회
    public List<Member> findAll() {
        String jpql = "select m from Member m";
        return em.createQuery(jpql, Member.class)
                  .getResultList();
    }

    // 회원리스트 조회 by name
    public List<Member> findByName(String name) {
        String jpql = "select m from Member m where m.name =:name";
        return em.createQuery(jpql, Member.class)
                 .setParameter("name", name)
                 .getResultList();
    }
}
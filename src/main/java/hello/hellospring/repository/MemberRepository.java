package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository { // 저장소

    Member save(Member member); // save를 통해 회원이 저장소에 저장됨
    Optional<Member> findById(Long id);
    // Optional이란? java8에 들어간 기능으로 findById나 Name의 값이 Null인 경우 Optional이라는 방법으로 감싸서 반환하도록 하는 것
    Optional<Member> findByName(String name);
    List<Member> findAll(); // findAll : 지금까지 저장된 모든 회원 List를 반환
}

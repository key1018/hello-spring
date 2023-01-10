package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository // 빈 생성
public class MemoryMemberRepository implements MemberRepository {

    private static Map<Long, Member> store = new HashMap<>();
    private static long sequence = 0L;


    @Override
    public Member save(Member member) {
        member.setId(++sequence); // store에 값을 넣기 전에 sequence(id)값 세팅
        store.put(member.getId(), member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(store.get(id)); // NPE를 방지하기 위해 Optional사용
    }

    @Override
    public Optional<Member> findByName(String name) {
        return store.values().stream()
                .filter(member -> member.getName().equals(name))
                .findAny(); // findAny : 하나라도 찾기
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values()); // Member들이 모두 반환됨
    }

    public void clearStore(){
        store.clear(); // 데이터를 비움
    }
}

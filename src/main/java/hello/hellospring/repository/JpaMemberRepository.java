package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import javax.persistence.EntityManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class JpaMemberRepository implements MemberRepository {

    private final EntityManager em; // jpa는 EntityManager로 모든 동작을 한다.

    public JpaMemberRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Member save(Member member) throws SQLException {
        em.persist(member); // persist : 영구 저장하닫
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        Member member = em.find(Member.class, id); // 조회할 타입, pk넣어서 조회
        return Optional.ofNullable(member);
    }

    /*
        기본적은 CRUD는 sql문을 작성할 필요가 없다.
        하지만 여러 개의 list를 돌리거나 pk기반이 아닌 것들은 sql문을 작성해야한다.
     */

    @Override
    public Optional<Member> findByName(String name) {
        List<Member> result = em.createQuery("select m from Member m where m.name = :name", Member.class) // Member.class : 조회할 타입
                                .setParameter("name", name)
                                .getResultList();

        return result.stream().findAny();
    }

    @Override
    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class).getResultList();
        // 객체를 대상으로 쿼리를 날림
        // selet ^m^ : Member라는 객체 자체를 조회함
    }
}

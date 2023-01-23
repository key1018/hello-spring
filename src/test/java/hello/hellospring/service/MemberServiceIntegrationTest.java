package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest // 스프링을 테스트할 때는 해당 어노테이션을 통해 쉽게 테스트 할 수 있다.
@Transactional
/*
테스트 케이스에 @Transactional를 붙이면, 테스트 시작 전에 트랜잭션을 시작하고, 테스트 완료 후에 항상 rollback함
단, 서비스나 컨트롤러에 붙으면 rollback하지않고 정상적으로 작동한다.
만약 rollback하지 않으려면 @Commit을 붙이면 됨
=> 다음 테스트를 반복해서 진핼할 수 있다.
 */
class MemberServiceIntegrationTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;
    /*
    MemoryMemberRepository memberRepository = new MemoryMemberRepository();
    => new로 다른 객체 리파지토리가 생성되면 다른 인스턴스이기 때문에 내용물이 달라질 수 있다.
       그러므로 같은 리파지토리를 이용하도록 설정해야된다!
    */

    @Test
    void 회원가입() throws SQLException {
        // Test는 한글로 바꿔도 상관없다

        // given (이런 상황이 주어져서)
        Member member = new Member();
        member.setName("hello");

        // when (이걸 실행했을 때)
        Long saveId = memberService.join(member);

        // then (결과는 이것이 나와야돼)
        Member findMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName()); // 이름 검증
    }

    @Test // 테스트는 예외처리가 제일 중요하다
    public void 중복_회원_예외() throws SQLException {

        // given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");

        // when
        memberService.join(member1);

        // 예외처리 방법2
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        // member2를 넣으면 IllegalStateException 예외가 발생해야됨

        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
    }


}
package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

// 단축키 : alt + enter
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

class MemberServiceTest {

    MemberService memberService;
    MemoryMemberRepository memberRepository;
    /*
    MemoryMemberRepository memberRepository = new MemoryMemberRepository();
    => new로 다른 객체 리파지토리가 생성되면 다른 인스턴스이기 때문에 내용물이 달라질 수 있다.
       그러므로 같은 리파지토리를 이용하도록 설정해야된다!
    */

    @BeforeEach
    public void beforeEach(){
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
        // 테스트가 실행될 때마다 MemberService와 같은 메모리 리퍼지토리가 사용된다.
    }

    @AfterEach
    public void afterEach(){
       memberRepository.clearStore();
    }

    @Test
    void 회원가입() {
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
    public void 중복_회원_예외(){

        // given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");

        // when
        memberService.join(member1);
        // memberService.join(member2); // 이름이 동일하므로 예외가 발생해야됨

        // then
        /*
         예외처리 방법1
        try {
            memberService.join(member2);
            fail();
        } catch (IllegalStateException e) {
            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
        }
        */

        // 예외처리 방법2
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        // member2를 넣으면 IllegalStateException 예외가 발생해야됨

        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
    }

    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}
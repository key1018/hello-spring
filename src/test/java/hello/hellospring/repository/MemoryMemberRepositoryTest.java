package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

class MemoryMemberRepositoryTest {

    MemoryMemberRepository repository = new MemoryMemberRepository();

    @AfterEach   // 하나의 메소드가 테스트 종료될 때마다 호출되어서 실행
    public void afterEach(){
        repository.clearStore(); // 호출될 때마다 저장소를 싹 비움 => 실행된 메소드의 순서가 관계없어짐
    }

    @Test
    public void save(){

        // 내가 저장한 것
        Member member = new Member();
        member.setName("spring");

        repository.save(member);

        // db에서 꺼낸 것
        Member result = repository.findById(member.getId()).get(); // Optional에서 값을 꺼낼때는 get()을 사용한다.

        // 검증하기
        // Assertions.assertEquals(member, result);

        // result와 member가 동일한지 확인
        Assertions.assertThat(member).isEqualTo(result);

        System.out.println("member : " + member.getId() + ", " + member.getName());
        System.out.println("result : " + result.getId() + ", " + result.getName());

    }

    @Test
    public void findByName(){
         Member member1 = new Member();
         member1.setName("spring1");
         repository.save(member1);

         Member member2 = new Member();
         member2.setName("spring2");
         repository.save(member2);

         Member result = repository.findByName("spring1").get();

         Assertions.assertThat(member1).isEqualTo(result);
         // 전체 코드를 돌리니 오류가 났다
        // 왜? 모든 테스트는 순서 상관없이 메소드별로 다 따로 동작하도록 설계해야됨
        // 현재 findAll이 먼저 실행됐으므로 spring1,2가 이미 저장됨
        // 그래서 findByName객체가 finaAll에서 이미 저장한 spring1이 나와 오류가 발생한 것!
        // 이를 해결하기 위해 테스트가 끝나면 데이터를 깔끔하게 clear해야됨
    }

    @Test
    public void findAll(){
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        List<Member> result = repository.findAll();

        Assertions.assertThat(result.size()).isEqualTo(2);
    }

}

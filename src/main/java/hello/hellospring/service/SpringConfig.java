package hello.hellospring.service;

import hello.hellospring.repository.JdbcMemberRepository;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class SpringConfig {

    private DataSource dataSource;

    public SpringConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean // Spring Bean을 등록한다는 의미
    public MemberService memberService(){ // memberService를 해당 로직을 호출해서 Spring Bean에 등록해줌
        return new MemberService(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository() {
       // return new MemoryMemberRepository();
        return new JdbcMemberRepository(dataSource);
    }

}

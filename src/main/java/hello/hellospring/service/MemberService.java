package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MemberService {
    /*
        회원 서비스란?
        회원 리포지토리랑 도메인을 활용해서 실제 비즈니스 로직을 작성하는 곳
    */

    private final MemberRepository memberRepository;

    // Constructor 단축키 : alt + insert
    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;  // 생성자를 이용해 외부에서 주입하기
    }

    /**
     * 회원 가입
     */
    public Long join(Member member) {
        // 조건 : 같은 이름이 있는 회원은 중복 가입x
        validateDuplicateMember(member); // 중복 회원 검증

        memberRepository.save(member); // 중복 아닌 경우 저장
        return member.getId(); // 아이디 반환
    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
                .ifPresent(m -> { // ifPresent:값이 존재하면(널이 아니면)
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }

    /**
     * 전체 회원 조회
     */
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    /**
     * 개별 회원 조회
     */
    public Optional<Member> findOne(Long memberId){
        return memberRepository.findById(memberId);
    }

}

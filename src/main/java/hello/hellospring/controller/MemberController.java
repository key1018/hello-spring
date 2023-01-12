package hello.hellospring.controller;

import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class MemberController {

    /*
         1. 필드 주입
         @Autowired private MemberService memberService;
     */

    /*
        2. setter주입
       @Autowired
        public void setMemberService(MemberService memberService) {
            this.memberService = memberService;
        }
    */

    /*   3. 생성자 주입
        생성자가 memberService를 필요로 한다는 사실 확인하여 스프링 컨테이너에 있는 memberService에 가져다가 연결시켜준다.
        Controller와 Service를 서로 연결시겨주는 역할(dependency injection)
    */

    private final MemberService memberService;
    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

}

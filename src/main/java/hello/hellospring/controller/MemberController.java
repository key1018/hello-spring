package hello.hellospring.controller;

import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class MemberController {

    private final MemberService memberService;

    @Autowired
    // 생성자에 해당 어노테이션이 있으면 스프링 컨테이너에 있는 memberService에 가져다가 연결시켜준다.
    // Controller와 Service를 서로 연결시겨주는 역할(dependency injection)
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }



}

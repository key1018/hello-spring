package hello.hellospring.controller;

import hello.hellospring.domain.Member;
import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.sql.SQLException;
import java.util.List;

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

    @GetMapping("/members/new") // get방식으로 통해 Mapping됨
    public String createForm(){
        return "members/createMemberForm";
    }

    /* get과 post의 차이
        - get : 조회할 때 주로 사용
        - post : 데이터를 form에 넣어서 전달할 때 주로 사용
     */

    @PostMapping("/members/new") // url은 동일하지만 html에서 post방식으로 전달했기 때문에 해당 메소드가 값을 전달받음
    public String create(MemberForm form) throws SQLException {
        Member member = new Member();
        member.setName(form.getName());

        System.out.println("member : " + member.getName());
        memberService.join(member);

        return "redirect:/"; // 시작페이지로 다시 넘어감
    }

    @GetMapping("/members")
    public String list(Model model){
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        // members안에 list로 모든 회원들을 조회해서 담아눔
        return "members/memberList";
    }
}

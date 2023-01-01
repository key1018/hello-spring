package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

    @GetMapping("hello") // 웹 어플리케이션에서 /hello를 선언하면 해당 메소드를 호출한다.
    public String hello(Model model) {
        model.addAttribute("data", "는 영어로 hello!");
        return "hello"; // localhost:9090/hello를 실행해라는 의미이다.
    }

    @GetMapping("hello-mvc")
    public String helloMvc(@RequestParam("name") String value, Model model) {
        // 사용자에게 value값을 직적 받는 GET방식
        // 키 : name, 값 : value
        model.addAttribute("name", value);
        return "hello-template";
    }

    @GetMapping("hello-string")
    @ResponseBody // Http통신 프로토콜 body부분에 데이터를 직접 넣어주겠다라는 의미
    public String helloString(@RequestParam("name") String name) {
        return "hello " + name; // spring을 요청하면 문자가 바로 내려가 "hello spring"으로 view에 생성됨
        // view가 필요없이 데이터가 그래도 송출됨
    }

    @GetMapping("hello-api")
    @ResponseBody
    public Hello helloApi(@RequestParam("name") String name) {
        Hello hello = new Hello();
        hello.setName(name);
        return hello; // Hello 객체를 넘김
    }

    static class Hello {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

}

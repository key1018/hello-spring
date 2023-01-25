package hello.hellospring.aop;

import ch.qos.logback.core.net.SyslogOutputStream;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect // AOP를 가능하게 하는 어노테이션
@Component // 스프링 빈으로 등록하는 어노테이션
public class TimeTraceAop {

    // @Around : 지정된 패턴에 해당하는 메소드의 실행되기 전, 실행된 후 모두에서 동작시키는 어노테이션. 메소드의 반환 값은 Object여야 한다.
    @Around("execution(* hello.hellospring..*(..))") // 패키지 하위에 있는 것은 모두 적용
    public Object execut(ProceedingJoinPoint joinPoint) throws Throwable{

        long start = System.currentTimeMillis();
        System.out.println("START : " + joinPoint.toString());
        try{
            return joinPoint.proceed();
        } finally {
            long finish = System.currentTimeMillis();
            long timeMs = finish - start;
            System.out.println("END : " + joinPoint.toString() + " " + timeMs + "ms");

        }
    }
}

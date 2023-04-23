package aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class helloAspect {
//    @Before("execution(* services.helloService.helloName(..))")
//    public void before(){
//        System.out.println("Before running helloName()");
//    }
//
//    @After("execution(* services.helloService.helloName(..))")
//    public void after(){
//        System.out.println("After running helloName()");
//    }
//    @Around("execution(* services.helloService.helloName(..))")
//    public Object around(ProceedingJoinPoint joinPoint){
//        System.out.println("Around aspect running");
//        return "something";
//    }

    @Around("execution(* services.helloService.helloName(..))")
    public Object around1(ProceedingJoinPoint joinPoint){
        System.out.println("Around aspect running");
        Object res = null;
        try{
            res = joinPoint.proceed();
            System.out.println("Object found");
        } catch (Throwable throwable){
            throwable.printStackTrace();
        }
        return res;
    }
}
// Joinpoint represents the method we are intercepting. If we are intercepting helloName(), then the
// joinPoint will be helloName(). You can also call joinPoint as a point during execution of a program/method

// around() aspect completely swallows the given joinPoint(helloName() here). That means whatever the helloName()
// func() is gonna return, is completely lost & is replaced by the return value of around() that is "something".
//
// In around1(), the aspect first prints "Around aspect running". Then the aspect will try to assign the value
// returned by joinPoint method(helloName() here) to Object res by using proceed() method.
// proceed() is a joinPoint method used to get a returned value by a joinPoint on which is called.
// In res = joinPoint.proceed(), res will be "Hello {name}" from helloName(name) func(). Then it prints
// "Object found"
//
// If joinPoint.proceed() doesnt returns a value, then the catch block will run & error will be printed
//
// From around() & around1() aspect methods, only around() will run because it is first found(written in code)
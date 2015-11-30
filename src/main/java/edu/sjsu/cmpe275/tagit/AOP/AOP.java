package edu.sjsu.cmpe275.tagit.AOP;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
public class AOP {
    @Before("execution(* edu.sjsu.cmpe275.tagit.controllers.*.*(..))")
    public void beforeLogging(JoinPoint joinPoint){
        try{
            System.out.println("Entering API method: "+joinPoint.getSignature().getName() + "()");
        }catch(Throwable e){
            e.printStackTrace();
        }
    }

    @After("execution(* edu.sjsu.cmpe275.tagit.controllers.*.*(..))")
    public void afterLogging(JoinPoint joinPoint){
        try{
            System.out.println("Exiting API method: "+joinPoint.getSignature().getName() + "()");
        }catch(Throwable e){
            e.printStackTrace();
        }
    }

}

package edu.sjsu.cmpe275.tagit.AOP;

/**
 * Created by Cherisha on 11/29/15.
 */

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
public class TagitLogin {
    @Before("execution(* edu.sjsu.cmpe275.tagit.controllers.*.*(..))")
    public void tagitBeforeLogging(JoinPoint joinPoint){
        try{
            System.out.println("Entering the API to execute "+joinPoint.getSignature().getName()+" method");
        }catch(Throwable e){
            e.printStackTrace();
        }
    }

    @After("execution(* edu.sjsu.cmpe275.tagit.controllers.*.*(..))")
    public void qtagitAfterLogging(JoinPoint joinPoint){
        try{
            System.out.println("Exiting the API after execution of "+joinPoint.getSignature().getName()+" method");
        }catch(Throwable e){
            e.printStackTrace();
        }
    }

}

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
    @Before("execution(* com.quiz.implementation.*.*(..))")
    public void quizAppBasicBeforeLogging(JoinPoint joinPoint){
        try{
            System.out.println("Entering function "+joinPoint.getSignature().getName());
        }catch(Throwable e){
            e.printStackTrace();
        }
    }

    @After("execution(* com.quiz.implementation.*.*(..))")
    public void quizAppBasicAfterLogging(JoinPoint joinPoint){
        try{
            System.out.println("Exiting function "+joinPoint.getSignature().getName());
        }catch(Throwable e){
            e.printStackTrace();
        }
    }

}

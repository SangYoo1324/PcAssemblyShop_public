package com.example.pcAssemblyShop.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
@Slf4j
public class PerformanceAspect {
    //RunningTime 이란 Annotation  지정
    @Pointcut("@annotation(com.example.pcAssemblyShop.annotation.RunningTime)")
    private void enableRunningTime(){

    }
    // 기본 패키지의 모든 메소드
    @Pointcut("execution(* com.example.pcAssemblyShop..*(..))")  // every class
    private void cut(){

    }
    //두 조건을 만족하는 대상을 전후로 부가기능을 삽입
    @Around("cut() && enableRunningTime()")  // 위에 두 method 다 적용
    public void loggingRunningTime(ProceedingJoinPoint joinPoint) throws Throwable {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        // method 수행 전, 측정 시작
        Object returningObj = joinPoint.proceed();

        //method names
        String methodName = joinPoint.getSignature().getName();

        // mehtod 수행 후, 측정종료 및 로깅
        log.info("{}'s total runnign time => {} sec", methodName, stopWatch.getTotalTimeSeconds());
    }
}

//package com.example.pcAssemblyShop.aop;
//
//import lombok.extern.slf4j.Slf4j;
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.annotation.AfterReturning;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Before;
//import org.aspectj.lang.annotation.Pointcut;
//import org.springframework.stereotype.componentnt;
//
//@Aspect
//@Component
//@Slf4j
//public class AuthAspect {
//
////    @Pointcut("execution(* com.example.pcAssemblyShop.controller.PageController.mainPage(*))")
//@Pointcut("execution(* com.example.pcAssemblyShop.controller.PageController.*(..))")
//    private void cut(){
//
//    }
//
//    @Before("cut()")
//    public void loggingArgs(JoinPoint joinpoint){
//        //input
//        Object[] args = joinpoint.getArgs();
//        //class name
//       String className = joinpoint.getTarget()
//                .getClass()
//                .getSimpleName();
//        //method name
//         String methodName = joinpoint.getSignature().getName();
//        // logging input
////        for(Object obj: args){
//        log.info("{} class's#{} method's input => {}",className,methodName,args.toString());
////        }
//        log.info("PointCut 들어갔다아아아!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
//
//    }
//
//
//    @AfterReturning(value = "cut()", returning = "returnObj")
//    public void loggingReturnValue(JoinPoint joinpoint ,Object returnObj){
//        // 반환값 로깅
//        //class name
//        String className = joinpoint.getTarget()
//                .getClass()
//                .getSimpleName();
//        //method name
//        String methodName = joinpoint.getSignature().getName();
//        log.info("{} class's#{} method's input => {}",className,methodName,returnObj);
////        }
//        log.info("PointCut returning 들어갔다아아아!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
//    }
//
//}

package com.bankingApp.j.aspect;

import org.aspectj.lang.annotation.*;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import java.util.Arrays;
import java.util.logging.Logger;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = Logger.getLogger(LoggingAspect.class.getName());

    @Around("execution(* com.bankingApp.j.controller.EmployeeController.*(..))") 
    public Object logMethodExecution(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String methodName = signature.getMethod().getName();
        Object[] args = joinPoint.getArgs();

        logger.info("Executing method: " + methodName + " with arguments: " + Arrays.toString(args));
        
        Object result = joinPoint.proceed(); // Continue with method execution
        
        logger.info("Method " + methodName + " executed successfully, returning: " + result);
        return result;
    }
}

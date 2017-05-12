package cn.zm.blog.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * Created by zm on 2017-4-18.
 */

@Component
@Aspect
public class TimeAspect {
    //@Around("bean(*Service)")
    //@Around("within(* cn.zm.blog.service.impl.*Impl)") 只能找到类
    @Around("execution(* cn.zm.blog.service.*Service.login(..))")//直接精确到具体方法
    public Object test(ProceedingJoinPoint jp) throws Throwable /*这里的抛出异常是事务回滚的前提，不然收不到异常*/{
        //使用jp的获取“方法签名”的方法获取方法信息
        Signature sig = jp.getSignature();
        //测试业务方法的性能
        long t1 = System.currentTimeMillis();
        Object val = jp.proceed();
        long t2 = System.currentTimeMillis();
        System.out.println(sig + "耗时:" + (t2 - t1)+"ms");

        return val;
    }
}

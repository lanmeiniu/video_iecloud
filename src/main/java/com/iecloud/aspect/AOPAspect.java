package com.iecloud.aspect;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * Created by lanmeiniu on 2017/7/24.
 */
@Aspect
@Component

public class AOPAspect {
    private Logger log = LoggerFactory.getLogger(AOPAspect.class);

    public AOPAspect() {
        System.out.println(">>>>>>>>>Catch an AOP<<<<<<<<<<");
        log.info(">>>>>>>>>>>>>>>Begin to Aop~~<<<<<<<<<<<<<<<");
    }
//    "execution(* com.netease.course.Caculator.*(..))

    /**
     *"execution(* hello(..))" 表示匹配所有目标类中的 hello() 方法
     */

    @Pointcut("execution(public * com.iecloud.controller.portal.*.*(..))")
    public void userControllerLogin () {
        System.out.println(">>>>>>>>>111111111111111Catch an AOP<<<<<<<<<<");
        log.info(">>>>>>>>>>>>>>UserController<<<<<<<<<<<<<<<<<");
    }

    @Before(value = "userControllerLogin()")
    public void beforeUserControllerLogin () {
        System.out.println("22222222222222222222Catch an beforeUserControllerLogin");
        log.info(">>>>>>>>>UserController_Before<<<<<<<<<<<<<<");
    }
    /**
     * within 匹配特定路径范围下的所有 join point
     *example:@Pointcut("within(com.iecloud)")匹配整个iecloud包下的所有方法
     */
    @Pointcut("within(com.iecloud.controller.portal.*)")
    public void showWithin() {

        log.info(">>>>>>>>>>showWithin_yController<<<<<<<<<<<<<");
    }
    /**
     * 当前joinpoint执行后 执行
     */
    @After("showWithin()")
    public void afterShowWithin() {
        log.info(">>>>>>>>>>>>Now_yController<<<<<<<<<<<<<<<<<");
    }
    /**
     * 成功执行JoinPoint 返回结果
     */
    @AfterReturning(pointcut = "showWithin()",returning = "retVal")
    public void afterReturnShowWithin(Object retVal) {
        log.info(">>>>>>>JoinPoint afterReturning showWithin<<<<<<" + (String) retVal);
    }

    /**
     * 异常执行JoinPoint 返回结果
     */
    @AfterThrowing(pointcut = "showWithin()",throwing = "ex")
    public void afterThrowWithin(RuntimeException ex)
    {
        log.info(">>>>>>>>>AfterThrowWithin异常返回结果<<<<<<<<<<<<<"+ex);
    }

    /**
     * this 匹配bean
     * this中使用的表达式必须是类型全限定名，不支持通配符
     */
    @Pointcut("this(com.iecloud.service.IUserService)")
    public void showThis() {
        log.info(">>>>>>>>>>>Begin_IUserService~~<<<<<<<<<<<<<<<<");
    }

    @Before("showThis()")
    public void beforeShowThis() {

        log.info(">>>>>>>>>>>>>>>beforeShowThis_IUserService<<<<<<<<<<<<<<<<<<");
    }

    /**
     * target 匹配当前目标对象，即需要织入advice的原始的类
     * TODO this 与 target 差别
     */
//    @Before("target(com.iecloud.service.IUserService)")
//    public void showTarget() {
//        log.info(">>>>>>>>>>>before showTarget<<<<<<<<<<<<<<<<<,");
//    }
   @Pointcut("execution(public * com.iecloud.controller.portal.*.*(..))")
    public void thisController() {
       log.info(">>>>>>>>>>>>>thisController<<<<<<<<<<<<");
   }
    /**
     * args 匹配参数满足要求的方法
     * 当前匹配thisController 下仅包含一个参数x的方法
     * args不匹配参数名称，仅匹配类型和参数顺序
     */
    @Before(value = "thisController() && args(cat)")
    public void beforeArgsOne (String cat) {
        log.info(">>>>>>>>>>>>beforeArgsOne<<<<<<<<<<<<<<< arg =" + cat);
    }
    /**
     * 当前匹配 thisController 下 第二个参数为String类型的执行方法
     */
    @Before(value = "thisController() && args(*,cat, ..)")
    public void beforeArgsTwo(String cat) {
        log.info(">>>>>>>>>>>>>>beforeArgsTwo<<<<<<<<<<<<<<<<" +cat);
    }
    /**
     * bean匹配 bean名字为指定值的bean下的所有方法
     */
    @Before(value = "bean(beanController)")
    public void beforeBean () {
        log.info(">>>>>>>>>>>beforeBean<<<<<<<<<<<<");
    }
}

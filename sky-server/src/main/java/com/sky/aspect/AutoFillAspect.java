package com.sky.aspect;

import com.sky.annotation.AutoFill;
import com.sky.constant.AutoFillConstant;
import com.sky.context.BaseContext;
import com.sky.enumeration.OperationType;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.time.LocalDateTime;

/**
 * @Author: tangjie
 * @Description: 自定义切面，实现公共字段自动填充处理逻辑
 * @DateTime: 2024/8/13 17:41
 **/
@Aspect
@Component
@Slf4j
public class AutoFillAspect {

    /**
     * 切入点：
     */
    @Pointcut("execution(* com.sky.mapper.*.*(..)) && @annotation(com.sky.annotation.AutoFill)")
    public void autoFillPointCut()
    {

    }

    /**
     * 前置通知,在通知中进行公共字段的赋值
     */
    @Before("autoFillPointCut()")
    public void autoFill(JoinPoint joinPoint)
    {
        log.info("开始进行公共字段自动填充...");
        //获取到当前被拦截的方法上的数据库操作类型
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();//方法签名对象
        AutoFill autoFill = signature.getMethod().getAnnotation(AutoFill.class);//获得方法上的注解对象
        OperationType operationType=autoFill.value();//获得数据库操作类型

        //获取当前被拦截方法的参数 --实体对象
        Object[] args = joinPoint.getArgs();
        if (args == null)
        {
            return ;
        }
        Object object=args[0];


        //准备赋值的数据 时间，当前登录用户的id
        LocalDateTime now = LocalDateTime.now();
        Long id = BaseContext.getCurrentId();

        //根据当前不同的操作类型，为对应的输出通过反射进行赋值
        if(operationType==OperationType.INSERT)
        {
            //为4个公共字段赋值
            try {
                Method setCreateTimes =object.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_TIME,LocalDateTime.class);
                Method setCreateUser =object.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_USER,Long.class);
                Method setUpdateUser =object.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER,Long.class);
                Method setUpdateTimes =object.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME,LocalDateTime.class);

                //通过反射为对象属性赋值
                setCreateTimes.invoke(object,now);
                setCreateUser.invoke(object,id);
                setUpdateTimes.invoke(object,now);
                setUpdateUser.invoke(object,id);


            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        else if(operationType==OperationType.UPDATE)
        {
            //为2个公共字段赋值
            try {
                Method setUpdateUser =object.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER,Long.class);
                Method setUpdateTimes =object.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME,LocalDateTime.class);

                //通过反射为对象属性赋值
                setUpdateTimes.invoke(object,now);
                setUpdateUser.invoke(object,id);

            } catch (Exception e) {
                throw new RuntimeException(e);
            }


        }
    }
}

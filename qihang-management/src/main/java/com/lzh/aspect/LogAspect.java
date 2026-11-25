package com.lzh.aspect;

import cn.hutool.core.convert.Convert;
import cn.hutool.json.JSONUtil;
import com.lzh.common.DataHolder;
import com.lzh.pojo.OperateLog;
import com.lzh.service.OperateLogService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;

@Component
@Aspect
public class LogAspect {
    @Autowired
    private OperateLogService logService;
    @Autowired
    private DataHolder<Map<String, Object>> dataHolder;
    @Around("execution(* com.lzh.controller.*Controller.add*(..))" +
            "|| execution(* com.lzh.controller.*Controller.update*(..))" +
            "|| execution(* com.lzh.controller.*Controller.delete*(..))")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        Object proceed = null;
        long start = System.currentTimeMillis();
        try {
            proceed = pjp.proceed();
            return proceed;
        }finally {
            Signature signature = pjp.getSignature();
            OperateLog log = new OperateLog();
            // 取：从当前线程的中转站拿拦截器放进去的令牌信息，拿到操作人
            Map<String, Object> claims = dataHolder.get();
            if (claims != null && claims.get("id") != null) {
                log.setOperateUserId(Convert.toInt(claims.get("id")));
            }
            log.setOperateTime(LocalDateTime.now());
            log.setClassName(signature.getDeclaringTypeName());
            log.setMethodName(signature.getName());
            Object [] args = pjp.getArgs();
            log.setMethodParams(args == null ? null : JSONUtil.toJsonStr(args));
            log.setReturnValue(proceed == null ? null : JSONUtil.toJsonStr(proceed));
            long end = System.currentTimeMillis();
            log.setCostTime(end - start);
            logService.saveOrUpdate(log);
        }
    }

}

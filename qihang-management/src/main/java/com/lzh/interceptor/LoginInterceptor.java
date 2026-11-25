package com.lzh.interceptor;


import com.aliyun.core.utils.StringUtils;
import com.lzh.common.DataHolder;
import com.lzh.common.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;

@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private DataHolder<Map<String, Object>> dataHolder;

    /**
     * 【放】令牌解析出来之后、Controller 执行之前放进当前线程的中转站
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 保险：线程池里的线程是复用的，先清掉上一个请求可能残留的数据
        dataHolder.remove();

        String token = request.getHeader("token");
        if (StringUtils.isBlank(token)) {
            response.setStatus(401);
            return false;
        }
        try {
            Map<String, Object> map = jwtUtil.parseToken(token);
            // 解析成功才放，401/410 这两种情况没解析出来，也就没有要清理的东西
            dataHolder.set(map);
        } catch (Exception e) {
            response.setStatus(410);
            return false;
        }
        return true;
    }

    /**
     * 【清】整个请求（含切面、Controller）执行完才清，所以在这里删；
     *      Tomcat 的线程是复用的，不删的话下一个请求会读到上一个用户的令牌
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        dataHolder.remove();
    }
}

package com.lzh.common;

import org.springframework.stereotype.Component;

/**
 * 线程内的数据中转站（令牌中转站）。
 * <p>
 * 一个请求一个线程：拦截器 preHandle 里 set、afterCompletion 里 remove，
 * 同一次请求内的切面、Controller、Service 都能 get 到同一份数据（本项目存的是 JWT 解析出来的 claims）。
 * <p>
 * 注意：这是单例 bean，内部只有一个 ThreadLocal，全项目只能约定存一种类型。
 * 不要再出现 {@code DataHolder<String>} 这种注入，类型擦除后拿到的是同一个实例，
 * get 出来会 ClassCastException。
 */
@Component
public class DataHolder<T> {
    // 本质是基于 ThreadLocal 的线程私有存储，每个线程都有自己的数据，互不干扰
    private final ThreadLocal<T> holder = new ThreadLocal<>();

    public void set(T data) {
        holder.set(data);
    }

    public T get() {
        return holder.get();
    }

    /**
     * 请求结束（afterCompletion）必须调用。
     * Tomcat 线程是复用的，不清理会让下一个请求读到上一个用户的数据（串号）。
     */
    public void remove() {
        holder.remove();
    }
}

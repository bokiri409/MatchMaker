package com.web.blog.security;

public interface AuthToken<T> {
    boolean validate();
    T getData();
}

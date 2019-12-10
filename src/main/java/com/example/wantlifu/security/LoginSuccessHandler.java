package com.example.wantlifu.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.util.Assert;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Set;

/**
 * @author 王志坚
 * @createTime 2019.12.07.11:19
 */
public class LoginSuccessHandler implements
        AuthenticationSuccessHandler {

    private final  LoginUrlEntryPoint loginUrlEntryPoint;

    public LoginSuccessHandler(LoginUrlEntryPoint loginUrlEntryPoint){
        this.loginUrlEntryPoint = loginUrlEntryPoint;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        String url = null;
        for (GrantedAuthority g : authorities){
            if( g.getAuthority().equals("ROLE_admin") ){
                url = "/admin/center";
                break;
            }
        }
        if(url == null)
            url = "/user/center";
        response.sendRedirect(url);
    }


}

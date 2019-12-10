package com.example.wantlifu.config;


import com.example.wantlifu.security.LoginFailureHandler;
import com.example.wantlifu.security.LoginSuccessHandler;
import com.example.wantlifu.security.LoginUrlEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author 王志坚
 * @createTime 2019.11.26.9:43
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true,prePostEnabled = true)  //开启 全局的 security授权
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    /**
     * 重写 config 指定 权限控制规则
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //资源访问 设置


        http.authorizeRequests()
                .anyRequest().permitAll()
//                .antMatchers("/admin/login").permitAll()    //管理员 登陆 入口
//                .antMatchers("/user/login").permitAll() //用户 登陆 入口
//                .antMatchers("/static/**").permitAll()  //静态资源 访问
//                .antMatchers("/admin/**").hasRole("admin")  //后台 控制 界面
//                .antMatchers("/user/**").hasAnyRole("user","admin") //用户 界面
//                .antMatchers("/api/**").hasAnyRole("user","admin")  //api接口
                .and()
                .formLogin()    //表单 登陆
                .loginPage("/index")// 自定义登录页面
                .loginProcessingUrl("/login")// 自定义登录路径
                .failureHandler(loginFailureHandler())
                .successHandler(loginSuccessHandler())
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/logout/success")
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(loginUrlEntryPoint())
                .accessDeniedPage("/403");

        http.csrf().disable();
        http.headers().frameOptions().sameOrigin();
    }

    @Bean
    public LoginUrlEntryPoint loginUrlEntryPoint(){
        return new LoginUrlEntryPoint("/user/login");
    }

    @Bean
    public LoginFailureHandler loginFailureHandler(){
        return new LoginFailureHandler(loginUrlEntryPoint());
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public LoginSuccessHandler loginSuccessHandler(){
        return new LoginSuccessHandler(loginUrlEntryPoint());
    }


}

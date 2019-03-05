package com.baltan.springboot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Description:
 *
 * @author Baltan
 * @date 2019-01-21 14:06
 */
@EnableWebSecurity // @EnableWebSecurity中已经包含@Configuration
public class MySecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        /**
         * 定制请求的授权规则
         */
        http.authorizeRequests()
                .antMatchers("/user/**").hasRole("vip")
                .antMatchers("/async/**").permitAll();
        /**
         * 开启自动配置的登录功能
         * 默认访问/login
         * 默认post形式的/login代表处理登录
         * 一旦定制loginPage，那么loginPage的post请求就是登录
         */
        http.formLogin();
//                .usernameParameter("username")
//                .passwordParameter("password")
//                .loginPage("/login").failureUrl("/login-error"); // 定制登录页
        /**
         * 开启自动配置的注销功能
         * 默认问/logout，并清空session，默认返回/login?logout页面
         */
        http.logout();
//                .logoutSuccessUrl("/"); // 定制注销成功后返回的页面
        /**
         * 开启自动配置的记住我功能
         * 登录成功以后将cookie发给浏览器保存，以后访问页面带上这个cookie，通过检查就能够免登录
         */
        http.rememberMe();
//                .rememberMeParameter("rememberMe"); // 定制记住我功能
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                /**
                 * 定义认证规则
                 * 参考：https://blog.csdn.net/canon_in_d_major/article/details/79675033
                 *      http://www.manongjc.com/article/25401.html
                 *      https://docs.spring.io/spring-security/site/docs/5.1.2.RELEASE/reference/htmlsingle
                 */
                .inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder())
                .withUser("zhangsan").password(bCryptPasswordEncoder.encode("123456")).roles("sb")
                .and()
                .withUser("lisi").password(bCryptPasswordEncoder.encode("123456")).roles("vip");
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

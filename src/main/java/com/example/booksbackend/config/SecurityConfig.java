package com.example.booksbackend.config;


import com.example.booksbackend.service.MyUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyUserDetailService myUserDetailService;

    /**
     * 注入數據源
     */
    @Autowired
    private DataSource dataSource;

    /**
     * 配置對象
     */
    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        return jdbcTokenRepository;
    }

    /**
     * 登入解密操作
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(myUserDetailService).passwordEncoder(password());
    }

    /**
     * 密碼加密
     */
    @Bean
    PasswordEncoder password() {
        return new BCryptPasswordEncoder();
    }


    /**
     * 登入操作
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.logout().logoutUrl("/logout").logoutSuccessUrl("/login").permitAll();

        http.formLogin()
                .loginPage("/login")//自定義登入頁面
                .loginProcessingUrl("/checkUser") //設定登入頁面的Url
                .defaultSuccessUrl("/index")//登入後導向
                .failureUrl("/login?error=true")//登入失敗後導向
                .and().authorizeRequests()
                .antMatchers("/index").hasAnyAuthority("admin")
                .and()
                .userDetailsService(myUserDetailService) // 处理自动登录逻辑
                .csrf().disable();


    }
}

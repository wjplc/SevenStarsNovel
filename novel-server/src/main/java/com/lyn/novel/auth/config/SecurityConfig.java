package com.lyn.novel.auth.config;

import com.lyn.novel.auth.filter.CustomUsernamePasswordAuthenticationFilter;
import com.lyn.novel.auth.filter.VerifyCodeAuthenticationFilter;
import com.lyn.novel.auth.handle.*;
import com.lyn.novel.auth.provider.UsernamePasswordAuthenticationProvider;
import com.lyn.novel.auth.provider.VerifyCodeAuthenticationProvider;
import com.lyn.novel.auth.service.CustomUserDetailsService;
import com.lyn.novel.filter.JwtAuthenticationTokenFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 放行的路径
     */
    private final String[] PATH_RELEASE = {
            "/login/**",
            "/user/register/**",
            "/error/**"
    };

    private final CustomUserDetailsService customUserDetailsService;

    private final CustomAuthenticationSuccessHandler authenticationSuccessHandler;

    private final CustomAuthenticationFailureHandler authenticationFailureHandler;

    private final CustomLogoutSuccessHandler customLogoutSuccessHandler;

    private final CustomAccessDeniedHandler customAccessDeniedHandler;

    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    private final JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        //关闭csrf
        http
                .csrf().disable();
        //配置路径访问权限
        http
                .authorizeRequests()
                    .mvcMatchers(PATH_RELEASE).permitAll()
                    .mvcMatchers("test/auth/1").hasAuthority("select")
                    .mvcMatchers("test/auth/2").hasAuthority("update")
                    .anyRequest().authenticated();

        http
                .exceptionHandling()
                        .accessDeniedHandler(customAccessDeniedHandler)
                        .authenticationEntryPoint(customAuthenticationEntryPoint);
        //退出登录
        http
                .logout()
                        .logoutUrl("/logout")
                                .logoutSuccessHandler(customLogoutSuccessHandler);
        //配置过滤器和处理器
        http
                .addFilterBefore(customUsernamePasswordAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
//                .authenticationProvider(usernamePasswordAuthenticationProvider())
                .addFilterBefore(verifyCodeAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
//                .authenticationProvider(verifyCodeAuthenticationProvider());
                .addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .authenticationProvider(usernamePasswordAuthenticationProvider())
                .authenticationProvider(verifyCodeAuthenticationProvider());
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CustomUsernamePasswordAuthenticationFilter customUsernamePasswordAuthenticationFilter() {
        CustomUsernamePasswordAuthenticationFilter usernamePasswordAuthenticationFilter = new CustomUsernamePasswordAuthenticationFilter();
        usernamePasswordAuthenticationFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler);
        usernamePasswordAuthenticationFilter.setAuthenticationFailureHandler(authenticationFailureHandler);
        return usernamePasswordAuthenticationFilter;
    }

    @Bean
    public VerifyCodeAuthenticationFilter verifyCodeAuthenticationFilter() {
        VerifyCodeAuthenticationFilter verifyCodeAuthenticationFilter = new VerifyCodeAuthenticationFilter();
        verifyCodeAuthenticationFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler);
        verifyCodeAuthenticationFilter.setAuthenticationFailureHandler(authenticationFailureHandler);
        return verifyCodeAuthenticationFilter;
    }

    @Bean
    public UsernamePasswordAuthenticationProvider usernamePasswordAuthenticationProvider() {
        UsernamePasswordAuthenticationProvider usernamePasswordAuthenticationProvider = new UsernamePasswordAuthenticationProvider();
        usernamePasswordAuthenticationProvider.setUserDetailsService(customUserDetailsService);
        usernamePasswordAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return usernamePasswordAuthenticationProvider;
    }

    @Bean
    public VerifyCodeAuthenticationProvider verifyCodeAuthenticationProvider() {
        VerifyCodeAuthenticationProvider verifyCodeAuthenticationProvider = new VerifyCodeAuthenticationProvider();
        verifyCodeAuthenticationProvider.setUserDetailsService(customUserDetailsService);
        return verifyCodeAuthenticationProvider;
    }

}

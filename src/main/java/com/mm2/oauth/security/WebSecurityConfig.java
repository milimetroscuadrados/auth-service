package com.mm2.oauth.security;

import com.mm2.oauth.security.filters.SocialFilters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableAuthorizationServer
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService; // todo replace injection with function

    @Autowired
    private SocialFilters socialFilters;

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/img/**", "/health", "/css/*", "/login/**", "/forgot", "/logout", "/oauth/*", "/users/**").permitAll()
                .anyRequest().authenticated()
            .and()
                .exceptionHandling()
                .authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login"))
            .and()
                .formLogin()
                    .loginPage("/login")
                    .permitAll()
            .and()
                .logout()
                .logoutSuccessHandler(
                    (request, response, authentication) -> {
                        String redirectUri = request.getParameter("redirectUri");

                        if (redirectUri == null) {
                            redirectStrategy.sendRedirect(request, response, "/login");
                        } else {
                            response.sendRedirect(redirectUri);
                        }
                    }
                )
                    .permitAll()
            .and()
                .csrf()
                    .disable()
//                    .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()) // todo enable csrf???
//            .and()
                .addFilterBefore(socialFilters.ssoFilter(), BasicAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}

package vn.techmaster.storyreadingwebsite.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import vn.techmaster.storyreadingwebsite.Service.CustomUserDetailsService;

@Configuration
@Order(1)
public class AdminSecurityConfig {

    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsService();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain1(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/").permitAll();

        http.antMatcher("/admin/**")
                .authorizeRequests().anyRequest().hasAuthority("ADMIN")
                .and()
                .formLogin()
                .loginPage("/admin/login")
                .usernameParameter("email")
                .loginProcessingUrl("/admin/login")
                .defaultSuccessUrl("/admin")
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/admin/logout")
                .logoutSuccessUrl("/");

        return http.build();
    }



}
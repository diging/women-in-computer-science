package edu.asu.diging.wic.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityContext extends WebSecurityConfigurerAdapter {
    
    @Override
    public void configure(WebSecurity web) throws Exception {
        web
        // Spring Security ignores request to static resources such as CSS or JS
        // files.
        .ignoring().antMatchers("/static/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
       http.formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/login/authenticate")
                .failureUrl("/login?error=bad_credentials")
                // Configures the logout function
                .and()
                .logout()
                .deleteCookies("JSESSIONID")
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .and().exceptionHandling().accessDeniedPage("/403")
                // Configures url based authorization
                .and()
                .authorizeRequests()
                // Anyone can access the urls
                .antMatchers("/", "/network", "/concept/**", "/resources/**", "/login", "/login/authenticate",
                        "/logout").permitAll()
                // The rest of the our application is protected.
                .antMatchers("/users/**", "/admin/**").hasRole("ADMIN")
                .anyRequest().hasRole("USER");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(4);
    }

}
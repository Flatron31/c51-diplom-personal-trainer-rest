package com.example.c51diplompersonaltrainerrest.configuration.security;

import com.example.c51diplompersonaltrainerrest.configuration.security.Jwt.JwtConfig;
import com.example.c51diplompersonaltrainerrest.configuration.security.Jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@PropertySource("classpath:securityEndpoint.properties")
//@EnableGlobalMethodSecurity(
//        prePostEnabled = true,
//        securedEnabled = true,
//        jsr250Enabled = true)
//        Свойство prePostEnabled включает аннотации Spring Security до/после.
//        Свойство secureEnabled определяет, следует ли включить аннотацию @Secured .
//        Свойство jsr250Enabled позволяет нам использовать аннотацию @RoleAllowed .
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;

    @Value("${ADMIN_ENDPOINT}")
    private String ADMIN_ENDPOINT;

    @Value("${USER_ENDPOINT}")
    private String USER_ENDPOINT;

    @Value("${LOGIN_ENDPOINT}")
    private String LOGIN_ENDPOINT;

    @Value("${DB_H2_ENDPOINT}")
    private String DB_H2_ENDPOINT;

    @Value("${PUBLIC_URLS}")
    private String[] PUBLIC_URLS;

    @Value("${ADMIN_ROLE_NAME}")
    private String ADMIN_ROLE_NAME;

    @Value("${USER_ROLE_NAME}")
    private String USER_ROLE_NAME;

    public SecurityConfiguration(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(LOGIN_ENDPOINT).permitAll()
                .antMatchers(ADMIN_ENDPOINT).hasAuthority("ADMIN_ROLE_NAME")
                .antMatchers(USER_ENDPOINT).hasAuthority("USER_ROLE_NAME")
                .antMatchers(HttpMethod.GET, PUBLIC_URLS).permitAll()
                .antMatchers(DB_H2_ENDPOINT).permitAll()
                .antMatchers("/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .apply(new JwtConfig(jwtTokenProvider));
        http
                .headers().frameOptions().sameOrigin();
    }
}

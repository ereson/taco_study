package com.ereson.toca.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

import javax.sql.DataSource;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 在内存中添加两个用户
     * @param auth
     * @throws Exception
     */
    protected void configureByMemory(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("buzz")
                .password("{noop}infinity")
                .authorities("ROLE_USER")
                .and()
                .withUser("woody")
                .password("{noop}bullseye")
                .authorities("ROLE_USER");
    }

//    @Autowired
//    DataSource dataSource;
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.jdbcAuthentication()
//                .dataSource(dataSource)
//                .usersByUsernameQuery(
//                        "select username, password, enabled from Users where username = ?"
//                ).authoritiesByUsernameQuery(
//                        "select username, authority from UserAuthorities where username = ?"
//                ).passwordEncoder(new StandardPasswordEncoder("53cr3t"));
//    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.ldapAuthentication()
                .userSearchBase("ou=people")
                .userSearchFilter("(uid={0})")
                .groupSearchBase("ou=groups")
                .groupSearchFilter("member={0}")
                .passwordCompare()
                .passwordEncoder(new BCryptPasswordEncoder())
                .and()
                .contextSource().url("dc=tacocloud,dc=com");

    }

}

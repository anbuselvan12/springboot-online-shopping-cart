package com.dicka.onlineshopping.springbootcore.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class ConfigurationSecuritySistem extends WebSecurityConfigurerAdapter{

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private DataSource dataSource;

    @Value("${spring.queries.users-query}")
    private String accountsLoginByUsers;

    @Value("${spring.queries.roles-query}")
    private String accountsLoginByRoles;

    @Autowired
    private AccessDeniedHandler accessDeniedHandler;

    //----JDBC SECURITY AUTHENTICATION QUERY----\\
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.jdbcAuthentication()
                .usersByUsernameQuery(accountsLoginByUsers)
                .authoritiesByUsernameQuery(accountsLoginByRoles)
                .dataSource(dataSource)
                .passwordEncoder(bCryptPasswordEncoder);
    }


    //---SECURITY ACCESS PERMISSION----\\
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/", "/index", "/login", "/registrasi",
                        "/productImage", "/addToCart").permitAll()
                .antMatchers("/adminProduct").hasAuthority("ROLE_ADMIN")
                .antMatchers("/home").hasAnyAuthority("ROLE_ADMIN", "ROLE_USER")
                .antMatchers("/cart").hasAuthority("ROLE_USER")
                .antMatchers("/adminShowProduct").hasAuthority("ROLE_ADMIN")
                .antMatchers("/adminDeleteProduct").hasAuthority("ROLE_ADMIN")
                .antMatchers("/adminProductCreate").hasAuthority("ROLE_ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .failureUrl("/login?error=true").defaultSuccessUrl("/home")
                .usernameParameter("username")
                .passwordParameter("password")
                .and().logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/index").and().exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler);
    }


    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/image/**");
    }
}

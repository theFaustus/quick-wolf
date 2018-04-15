package com.quickwolf.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.web.servlet.ViewResolver;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

@Configuration
@EnableWebSecurity
public class QuickWolfConfiguration extends WebSecurityConfigurerAdapter {

    @Bean
    public static NoOpPasswordEncoder passwordEncoder() {
        return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource())
                .usersByUsernameQuery("select email, password, enabled from wolf.users where email = ?")
                .authoritiesByUsernameQuery("select email, role from wolf.users where email = ?");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/login/**").not().authenticated()
                .antMatchers("/registerAsDriver/**").not().authenticated()
                .antMatchers("/registerAsPassenger/**").not().authenticated()
                .antMatchers("/css/**").permitAll()
                .antMatchers("/img/**").permitAll()
                .antMatchers("/js/**").permitAll()
                .antMatchers("/adminProfile/**").access("hasRole('ROLE_ADMIN')")
                .antMatchers("/addTrip/**").access("hasRole('ROLE_DRIVER')")
                .antMatchers("/driverProfile/**").access("hasRole('ROLE_DRIVER')")
                .antMatchers("/passengerProfile/**").access("hasRole('ROLE_USER')")
                .antMatchers("/searchTrip/**").access("hasRole('ROLE_USER')")
                .antMatchers("/bookTrip/**").access("hasRole('ROLE_USER')")
                .antMatchers("/bookingConfirmed/**").access("hasRole('ROLE_USER')")
                .antMatchers("/confirmTripBooking/**").access("hasRole('ROLE_USER')")
                .antMatchers("/listTrips/**").access("hasRole('ROLE_USER')")
                .antMatchers("/").access("hasAnyRole('ROLE_USER,ROLE_DRIVER,ROLE_ADMIN')")
                .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/processLogin")
                .usernameParameter("email")
                .passwordParameter("password")
                .defaultSuccessUrl("/")
                .permitAll()
                .and()
                .logout()
                .deleteCookies("remember-me")
                .logoutSuccessUrl("/login")
                .permitAll()
                .and()
                .rememberMe();
    }

    @Bean
    public DriverManagerDataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl("jdbc:postgresql://localhost:5432/quick-wolf");
        dataSource.setUsername("postgres");
        dataSource.setPassword("123456");
        dataSource.setDriverClassName("org.postgresql.Driver");
        return dataSource;
    }


}

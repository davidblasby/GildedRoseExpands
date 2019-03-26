package com.miw.gildedroseexpands;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/**
 * sets up the spring configuration
 *
 * We are very very simple;
 * a) us InMemoryUserDetailsManager and create two users
 *      guest (password)
 *      admin (admin)
 * b) setup BASIC authentication
 * c) we have a few items for the embedded h2-console
 */
@Configuration
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

        /**
         * Trivial user definition - should be using a real database and encoding passwords as hashes
         */
        @Bean
        public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
                return new InMemoryUserDetailsManager(
                        User.withDefaultPasswordEncoder().username("guest").password("password")
                                .authorities("ROLE_USER").build(),
                        User.withDefaultPasswordEncoder().username("admin").password("admin")
                                .authorities("ROLE_ADMIN", "ROLE_USER").build());
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
//                http.authorizeRequests().antMatchers("/items", "/items/**").permitAll()
//                        .antMatchers("/h2-console", "/h2-console/**", "/h2-console/**/**",
//                                "console", "console/**").hasRole("ADMIN") //REMOVE FOR PRODUCTION
//                        .antMatchers("/buy", "/buy/**").hasRole("USER")
//                        .antMatchers("/inventory", "/admin", "/admin/**", "/admin/**/**",
//                                "/admin/**/**/**").hasRole("ADMIN").and().httpBasic();

                http.authorizeRequests().antMatchers("/h2-console", "/h2-console/**", "/h2-console/**/**").hasRole("ADMIN");
                http.httpBasic();
                //required for h2-console
                http.csrf().disable();
                http.headers().frameOptions().disable();
        }

        //        @Override
        //        protected void configure(HttpSecurity httpSecurity) throws Exception {
        //                httpSecurity.authorizeRequests().antMatchers("/").permitAll().and()
        //                        .authorizeRequests().antMatchers("/h2-console/**").permitAll();
        //
        //                httpSecurity.csrf().disable();
        //                httpSecurity.headers().frameOptions().disable();
        //        }

        //        @Override
        //        protected void configure(HttpSecurity http) throws Exception {
        //               http.authorizeRequests()
        //                       .anyRequest().permitAll()
        //               .and().httpBasic();
        //        }
}

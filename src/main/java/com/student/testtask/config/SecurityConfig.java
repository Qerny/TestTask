package com.student.testtask.config;

import com.student.testtask.dbentities.User;
import com.student.testtask.repositories.UserRepository;
import com.student.testtask.repositories.UserRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    /*@Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return  new BCryptPasswordEncoder();
    }*/

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        /*JdbcTemplate jdbcTemplate = new JdbcTemplate();
        User user = new User();
        user.setId("2");
        user.setLogin("admin");
        user.setPassword(passwordEncoder().encode("admin"));
        UserRepositoryImpl userRepository = new UserRepositoryImpl(jdbcTemplate);
        userRepository.setDataSource(dataSource);
        userRepository.createTable();
        userRepository.save(user);
        UserRepositoryImpl userRepository = new UserRepositoryImpl(jdbcTemplate);
        userRepository.setDataSource(dataSource);
        List<User> list = userRepository.findAll();*/

        auth
                .inMemoryAuthentication()
                    .withUser("user").password("user").roles("USER").and()
                    .withUser("admin").password("admin").roles("USER", "ADMIN");

        /*auth.jdbcAuthentication()
                .dataSource(dataSource)
                .passwordEncoder(NoOpPasswordEncoder.getInstance())
                .usersByUsernameQuery("select login, password from user where login=?")
                .authoritiesByUsernameQuery("select login, 'USER' from user where login=?");*/
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/**").fullyAuthenticated().and().httpBasic();

        //http.authorizeRequests().antMatchers("/**").hasAuthority("USER").anyRequest().authenticated().and().httpBasic();

        /*http
                .authorizeRequests()
                .antMatchers("/signup").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
                .logout()
                .permitAll();
                */
    }

    @Bean
    public NoOpPasswordEncoder passwordEncoder() {
        return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
    }
}

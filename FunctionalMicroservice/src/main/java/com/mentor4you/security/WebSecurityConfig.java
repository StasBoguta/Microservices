package com.mentor4you.security;

import com.mentor4you.security.jwt.JwtConfigurer;
import com.mentor4you.security.jwt.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true,
        jsr250Enabled = true)

public class WebSecurityConfig extends WebSecurityConfigurerAdapter implements WebMvcConfigurer {

    private final JwtFilter jwtFilter;

    @Autowired
    JwtConfigurer jwtConfigurer;


    public WebSecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //виключив csrf захист щоб можна було надсилати POST запити
                .cors().and().csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                //TODO  chek all permits

                .authorizeRequests().antMatchers("/api/auth/*",

                                                 "/api/registration",
                                                 "/api/users",
                                                 "/api/users/getAllBannedUser",
                                                 "/api/users/changeBanToUser",
                                                 "/api/searchMentor",//dell,
                                                 "/api/users/Test/{{id}}/{{name}}",//dell
                                                 "/api/searchMentor/Test/{{contactLast}}/{{contactFirst}}",//dell
                                                 "/api/mentors",//dell
                                                 "/api/searchMentor/filterGetListSmallMentors/{{city}}/{{categoryName}}/{{language}}/{{minPrice}}/{{maxPrice}}",//dell
                                                 "/api/searchMentor/findMentorsBestRating/{{number}}",//dell
                                                 "/api/users/changeUser",//dell
                                                 "/api/users/changeAvatar",
                                                 "/api/users/changeRole",
                                                 "/api/admin/appointModerator",
                                                 "/api/users/uploadAvatar",
                                                 "/api/users/deleteAvatar",
                                                 "/api/mentors",
                                                 "/api/emailToModerator/sendEmailToModer",// for ANY USER
                                                 "/api/mentees/**",
                                                 "/findmessage/{{sendid}}/{{recivid}}",
                                                 "/api/mentors/{id}",
                                                 "/sendSecurityEmail/{{sendTo}}",
                                                 "/api/admin/addCategory",
                                                 "/api/admin/deleteCategory"
                                                 ).permitAll()


                .and()
                .authorizeRequests().antMatchers("/api/**").authenticated()
                .anyRequest().permitAll()
                .and()
                .apply(jwtConfigurer)
        ;

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:4200")
                .allowedMethods("*");
    }


    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
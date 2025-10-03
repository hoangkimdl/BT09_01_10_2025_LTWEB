package vn.iostar.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import vn.iostar.repository.UserInfoRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    UserInfoRepository repository;

    // Authentication
    @Bean
    UserDetailsService userDetailsService() {
        return new UserInfoService(repository);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean 
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login", "/user/new").permitAll()   // cho phép login và tạo user
                        .requestMatchers("/hello").authenticated()            // hello cần login
                        .requestMatchers("/customer/**").authenticated()      // customer cũng cần login
                        .anyRequest().authenticated()                         // các request khác đều cần login
                )
                .formLogin(form -> form
                        .defaultSuccessUrl("/hello", true) // luôn vào hello sau khi login
                        .permitAll()
                )
                .logout(logout -> logout.permitAll())
                .build();
    }
}

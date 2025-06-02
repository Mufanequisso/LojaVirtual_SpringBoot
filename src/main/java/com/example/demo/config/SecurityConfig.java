
package com.example.demo.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())  // Desativa CSRF
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll()  // Permite tudo
                )
                .formLogin(form -> form.disable())  // Desativa formulário de login
                .httpBasic(httpBasic -> httpBasic.disable());  // Desativa autenticação básica

        return http.build();
    }
}

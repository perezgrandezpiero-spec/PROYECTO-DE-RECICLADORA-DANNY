package com.proyecto.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(request -> request
                .requestMatchers("/css/**", "/js/**", "/img/**", "/vendor/**").permitAll()
                .requestMatchers("/login", "/public/**").permitAll()
                .requestMatchers("/web/categorias/**", "/web/trabajadores/**").hasAuthority("ADMIN")
                .requestMatchers("/web/clientes/**", "/web/ventas/**").hasAnyAuthority("ADMIN", "GESTOR")
                .requestMatchers("/web/materiales/**").hasAnyAuthority("ADMIN", "GESTOR")
                .requestMatchers("/web/proveedores/**", "/web/compras/**", "/web/transformaciones/**")
                    .hasAnyAuthority("ADMIN", "GESTOR", "OPERARIO")
                .anyRequest().authenticated())
            .formLogin(form -> form
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/web/home", true)
                .failureUrl("/login?error=true")
                .permitAll())
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout=true")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll())
            .csrf(csrf -> csrf.disable());

        return http.build();
    }

	// Importar: org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
	// Importar: org.springframework.security.crypto.password.PasswordEncoder;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
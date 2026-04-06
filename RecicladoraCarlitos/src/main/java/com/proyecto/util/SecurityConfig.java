package com.proyecto.util;

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
		http.authorizeHttpRequests(request -> request
				// 1. Recursos estáticos (Públicos)
				.requestMatchers("/css/**", "/js/**", "/img/**", "/vendor/**").permitAll()

				// 2. Login (Público)
				.requestMatchers("/login", "/public/**").permitAll()

				// 3. REGLAS DE NEGOCIO (Aquí cerramos las puertas traseras) 🛡️

				// ADMINISTRACIÓN: Solo ADMIN
				.requestMatchers("/web/categorias/**", "/web/trabajadores/**").hasAuthority("ADMIN")

				// GESTIÓN: ADMIN o GESTOR
				.requestMatchers("/web/clientes/**", "/web/ventas/**").hasAnyAuthority("ADMIN", "GESTOR")

				// LOGÍSTICA (Materiales): ADMIN o GESTOR
				.requestMatchers("/web/materiales/**").hasAnyAuthority("ADMIN", "GESTOR")

				// OPERACIÓN y TRANSFORMACIÓN: ADMIN, GESTOR u OPERARIO
				.requestMatchers("/web/proveedores/**", "/web/compras/**", "/web/transformaciones/**")
				.hasAnyAuthority("ADMIN", "GESTOR", "OPERARIO")

				// 4. Todo lo demás requiere login (Home, Logout, etc.)
				.anyRequest().authenticated())
				.formLogin(form -> form.loginPage("/login").defaultSuccessUrl("/web/home", true) // Asegura que vayan al
																									// home
						.permitAll());
		return http.build();
	}

	// Importar: org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
	// Importar: org.springframework.security.crypto.password.PasswordEncoder;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
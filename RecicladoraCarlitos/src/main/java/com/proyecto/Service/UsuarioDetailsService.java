package com.proyecto.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.proyecto.Model.Usuario;
import com.proyecto.Repository.UsuarioRepository;

import jakarta.transaction.Transactional;

import java.util.List;

@Service
public class UsuarioDetailsService implements UserDetailsService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String nombreUsuario) throws UsernameNotFoundException {
		System.out.println(">>> Buscando usuario: " + nombreUsuario);
		Usuario usuario = usuarioRepository.findByNombreUsuario(nombreUsuario)
				.orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + nombreUsuario));
		System.out.println(">>> ENTRANDO A loadUserByUsername con: [" + nombreUsuario + "]");
		System.out.println(">>> Usuario encontrado: " + usuario.getNombreUsuario());
		System.out.println(">>> Contraseña en BD: " + usuario.getContra());
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		System.out.println(">>> ¿Coincide admin123? " + encoder.matches("admin123", usuario.getContra()));
		System.out.println(">>> Activo: " + usuario.isActivo());
		System.out.println(">>> Rol: " + usuario.getRol().getNombre());

		if (!usuario.isActivo()) {
			throw new UsernameNotFoundException("Usuario inactivo: " + nombreUsuario);
		}

		SimpleGrantedAuthority authority = new SimpleGrantedAuthority(usuario.getRol().getNombre());

		return new User(usuario.getNombreUsuario(), usuario.getContra(), List.of(authority));
	}
}
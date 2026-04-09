package com.proyecto.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

		Usuario usuario = usuarioRepository.findByNombreUsuario(nombreUsuario)
				.orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + nombreUsuario));

		if (!usuario.isActivo()) {
			throw new UsernameNotFoundException("Usuario inactivo: " + nombreUsuario);
		}

		SimpleGrantedAuthority authority = new SimpleGrantedAuthority(usuario.getRol().getNombre());

		return new User(usuario.getNombreUsuario(), usuario.getContra(), List.of(authority));
	}
}
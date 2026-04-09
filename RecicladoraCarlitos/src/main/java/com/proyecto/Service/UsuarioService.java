package com.proyecto.Service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.proyecto.Model.Rol;
import com.proyecto.Model.Usuario;
import com.proyecto.Repository.RolRepository;
import com.proyecto.Repository.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository userRepository;

	@Autowired
	private RolRepository rolRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public List<Usuario> listarTodos() {
		return userRepository.findAll();
	}

	public List<Usuario> listarActivos() {
		return userRepository.findByActivoTrue();
	}

	public List<Usuario> listarPorRol(Integer rolId) {
		return userRepository.findByRolId(rolId);
	}

	public List<Usuario> listarActivosPorRol(Integer rolId) {
		return userRepository.findByRolIdAndActivoTrue(rolId);
	}

	public void guardar(Usuario t) {
		if (t.getId() != null && (t.getContra() == null || t.getContra().isEmpty())) {

			userRepository.findById(t.getId()).ifPresent(existing -> {
				t.setContra(existing.getContra());
			});
		} else {
			// Nuevo usuario o cambio de contraseña: Encriptar
			t.setContra(passwordEncoder.encode(t.getContra()));
		}
		userRepository.save(t);
	}

	public Usuario buscarPorId(Integer id) {
		return userRepository.findById(id).orElse(null);
	}

	public void eliminar(Integer id) {
		Usuario t = buscarPorId(id);
		if (t != null) {
			t.setActivo(false);
			userRepository.save(t);
		}
	}

	public Usuario validarCredenciales(String nombre, String pass) {
		Usuario t = userRepository.findByNombreUsuario(nombre).orElseThrow();

		if (t != null && t.isActivo() && passwordEncoder.matches(pass, t.getContra())) {
			return t;
		}
		return null;
	}

	public List<Rol> listarRoles() {
		return rolRepository.findAll();
	}
}
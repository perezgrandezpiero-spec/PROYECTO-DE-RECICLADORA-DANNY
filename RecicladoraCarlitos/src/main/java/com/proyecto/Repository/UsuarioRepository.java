package com.proyecto.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyecto.Model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

	Optional<Usuario> findByNombreUsuario(String username);

	Usuario findByNroDocumento(String dni);

	List<Usuario> findByActivoTrue();

	List<Usuario> findByRolId(Integer rolId);

	List<Usuario> findByRolIdAndActivoTrue(Integer rolId);
}
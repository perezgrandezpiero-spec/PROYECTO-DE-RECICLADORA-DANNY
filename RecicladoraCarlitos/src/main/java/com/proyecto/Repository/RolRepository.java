package com.proyecto.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyecto.Model.Rol;

@Repository
public interface RolRepository extends JpaRepository<Rol, Integer> {
	Rol findByNombre(String nombre);

}
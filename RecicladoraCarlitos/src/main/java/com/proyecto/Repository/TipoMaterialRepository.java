package com.proyecto.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyecto.Model.TipoMaterial;

@Repository
public interface TipoMaterialRepository extends JpaRepository<TipoMaterial, Integer> {

	TipoMaterial findByNombre(String nombre);

	List<TipoMaterial> findByActivoTrue();
}
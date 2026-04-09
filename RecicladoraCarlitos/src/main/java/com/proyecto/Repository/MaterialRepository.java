package com.proyecto.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyecto.Model.Material;

@Repository
public interface MaterialRepository extends JpaRepository<Material, Integer> {

	List<Material> findByNombre(String nombre);

	List<Material> findByTipoMaterialNombre(String nombre);

	int countByTipoMaterialNombre(String nombre);

	List<Material> findByStockLessThan(Double min);
	
	List<Material> findByActivoTrue();
}

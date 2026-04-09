package com.proyecto.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.proyecto.Model.Material;
import com.proyecto.Repository.MaterialRepository;

@Service
public class MaterialService {

	@Autowired
	private MaterialRepository materialRepository;

	public List<Material> listarTodo() {
		return materialRepository.findAll();
	}

	public Optional<Material> buscarPorId(@RequestParam Integer id) {
		return materialRepository.findById(id);
	}

	public List<Material> listarActivosPorTipo(@RequestParam String nombre) {
		return materialRepository.findByTipoMaterialNombre(nombre);
	}

	public List<Material> listarResiduos() {
		return materialRepository.findByTipoMaterialNombre("RESIDUO");
	}

	public List<Material> listarProductos() {
		return materialRepository.findByTipoMaterialNombre("PRODUCTO");
	}

	public int contarProductos() {
		return materialRepository.countByTipoMaterialNombre("PRODUCTO");
	}

	public int contarResiduos() {
		return materialRepository.countByTipoMaterialNombre("RESIDUO");
	}

	public List<Material> buscarStockBajo(Double min) {
		return materialRepository.findByStockLessThan(min);
	}

	public List<Material> listarActivos() {
		return materialRepository.findByActivoTrue();
	}

}

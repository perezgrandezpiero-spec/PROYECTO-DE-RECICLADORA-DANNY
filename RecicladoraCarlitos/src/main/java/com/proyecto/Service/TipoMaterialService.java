package com.proyecto.Service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.Model.TipoMaterial;
import com.proyecto.Repository.TipoMaterialRepository;

@Service
public class TipoMaterialService {

	@Autowired
	private TipoMaterialRepository categoriaRepository;

	public List<TipoMaterial> listarTodas() {
		return categoriaRepository.findAll();
	}

	public List<TipoMaterial> listarActivas() {
		return categoriaRepository.findByActivoTrue();
	}

	public void guardar(TipoMaterial categoria) {
		categoriaRepository.save(categoria);
	}

	public TipoMaterial buscarPorId(Integer id) {
		return categoriaRepository.findById(id).orElse(null);
	}

	public void eliminar(Integer id) {
		TipoMaterial categoria = buscarPorId(id);
		if (categoria != null) {
			//categoria.setActivo(false);
			categoriaRepository.save(categoria);
		}
	}
}
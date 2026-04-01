package com.proyecto.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.Model.Material;
import com.proyecto.Repository.MaterialRepository;

@Service
public class MaterialService {

	@Autowired
	private MaterialRepository materialRepository;

	public List<Material> listarTodo() {
		return materialRepository.findAll();
	}

}

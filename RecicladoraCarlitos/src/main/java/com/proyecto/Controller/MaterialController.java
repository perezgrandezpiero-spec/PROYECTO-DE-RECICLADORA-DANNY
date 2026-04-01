package com.proyecto.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.proyecto.Service.MaterialService;

@Controller
@RequestMapping("/MaterialController")
public class MaterialController {

	@Autowired
	private MaterialService materialService;
	
	@GetMapping
	public String Material() {
		return "error";
	}
	
	@GetMapping("/listar")
	public String error(Model model) {
		
		model.addAttribute("materiales",materialService.listarTodo());
		return "listarMaterial";
	}
	

}
	
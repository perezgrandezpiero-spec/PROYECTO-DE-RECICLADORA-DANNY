package com.proyecto.Controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.proyecto.Model.TipoMaterial;
import com.proyecto.Service.TipoMaterialService;

@Controller
@RequestMapping("/web/categorias")
public class TipoMaterialController {

	@Autowired
	private TipoMaterialService categoriaService;

	@GetMapping
	public String listar(@RequestParam(required = false) Boolean mostrarTodos, Model model) {
		boolean mostrar = (mostrarTodos != null) && mostrarTodos;

		if (mostrar) {
			model.addAttribute("categorias", categoriaService.listarTodas());
		} else {
			model.addAttribute("categorias", categoriaService.listarActivas());
		}

		model.addAttribute("mostrarTodos", mostrar);
		return "categorias/listaCategorias";
	}

	@GetMapping("/nueva")
	public String nueva(Model model) {
		model.addAttribute("categoria", new TipoMaterial());
		return "categorias/formularioCategorias";
	}

	@PostMapping("/guardar")
	public String guardar(@ModelAttribute TipoMaterial categoria) {
		categoriaService.guardar(categoria);
		return "redirect:/web/categorias";
	}

	@GetMapping("/{id}/editar")
	public String editar(@PathVariable Integer id, Model model) {
		Optional<TipoMaterial> opcionCategoria = Optional.ofNullable(categoriaService.buscarPorId(id));
		if (opcionCategoria.isPresent()) {
			model.addAttribute("categoria", opcionCategoria.get());
			return "categorias/formularioCategorias";
		}
		return "redirect:/web/categorias";
	}

	@GetMapping("/{id}/eliminar")
	public String eliminar(@PathVariable Integer id) {
		categoriaService.eliminar(id);
		return "redirect:/web/categorias";
	}
}
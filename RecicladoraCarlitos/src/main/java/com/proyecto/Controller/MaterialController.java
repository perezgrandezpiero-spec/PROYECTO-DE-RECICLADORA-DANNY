package com.proyecto.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller("/MaterialController")
@RestController
public class MaterialController {

	@GetMapping
	public String Material() {
		return "/Inicio";
	}

}

package com.proyecto.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/LoginController")
public class LoginController {
	
	@GetMapping
	public String login() {
		return "login";
	}
}

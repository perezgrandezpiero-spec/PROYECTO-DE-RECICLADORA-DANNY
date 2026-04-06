package com.proyecto.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.reciclaje.model.Cliente;
import com.reciclaje.service.ClienteService;

@Controller
@RequestMapping("/web/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

   
    @GetMapping
    public String listar(@RequestParam(required = false) Boolean mostrarTodos, Model model) {
       
        boolean mostrar = (mostrarTodos != null) && mostrarTodos;

        if (mostrar) {
            model.addAttribute("clientes", clienteService.listarTodos());
        } else {
            model.addAttribute("clientes", clienteService.listarActivos());
        }

        model.addAttribute("mostrarTodos", mostrar);
        return "clientes/listaClientes";
    }

   
    @GetMapping("/registrar")
    public String registrar(Model model) {
        Cliente c = new Cliente();
        c.setActivo(true);
        model.addAttribute("cliente", c);
        return "clientes/formularioClientes";
    }

    
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Integer id, Model model) {
        Cliente cliente = clienteService.buscarPorId(id);

        
        if (cliente == null) {
            return "redirect:/web/clientes";
        }

        model.addAttribute("cliente", cliente);
        return "clientes/formularioClientes"; 
    }

    
    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Cliente cliente) {
        
        clienteService.guardar(cliente);
        return "redirect:/web/clientes";
    }

    
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Integer id) {
        clienteService.eliminar(id); 
        return "redirect:/web/clientes";
    }
}
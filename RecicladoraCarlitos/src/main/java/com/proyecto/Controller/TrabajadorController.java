package com.proyecto.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.reciclaje.model.Trabajador;
import com.reciclaje.repository.RolRepository; // O RolService si lo tienes creado
import com.reciclaje.service.TrabajadorService;

@Controller
@RequestMapping("/web/trabajadores")
public class TrabajadorController {

    @Autowired
    private TrabajadorService trabajadorService;

    @Autowired
    private RolRepository rolRepository;

    // 1. LISTAR
    @GetMapping
    public String listar(@RequestParam(required = false) Boolean mostrarTodos,
            @RequestParam(required = false) Integer rolId,
            Model model) {
        boolean mostrar = (mostrarTodos != null) && mostrarTodos;

        if (rolId != null) {
            if (mostrar) {
                model.addAttribute("trabajadores", trabajadorService.listarPorRol(rolId));
            } else {
                model.addAttribute("trabajadores", trabajadorService.listarActivosPorRol(rolId));
            }
            model.addAttribute("rolSeleccionado", rolId);
        } else {
            if (mostrar) {
                model.addAttribute("trabajadores", trabajadorService.listarTodos());
            } else {
                model.addAttribute("trabajadores", trabajadorService.listarActivos());
            }
        }

        model.addAttribute("mostrarTodos", mostrar);
        model.addAttribute("roles", rolRepository.findAll());
        return "trabajadores/listaTrabajadores";
    }

    @GetMapping("/registrar")
    public String registrar(Model model) {
        Trabajador t = new Trabajador();
        t.setActivo(true);
        model.addAttribute("trabajador", t);

        model.addAttribute("roles", rolRepository.findAll());
        return "trabajadores/formularioTrabajadores";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Integer id,
            @RequestParam(required = false) Integer rolId,
            @RequestParam(required = false) Boolean mostrarTodos,
            Model model) {
        Trabajador t = trabajadorService.buscarPorId(id);
        if (t == null) {
            return "redirect:/web/trabajadores";
        }
        t.setPass(""); // Limpiar password para evitar enviar el hash a la vista
        model.addAttribute("trabajador", t);

        model.addAttribute("roles", rolRepository.findAll());

        model.addAttribute("filtroRolId", rolId);
        model.addAttribute("filtroMostrarTodos", mostrarTodos);

        return "trabajadores/formularioTrabajadores";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Trabajador trabajador,
            @RequestParam(required = false) Integer filtroRolId,
            @RequestParam(required = false) Boolean filtroMostrarTodos,
            org.springframework.web.servlet.mvc.support.RedirectAttributes redirectAttributes) {

        trabajadorService.guardar(trabajador);

        if (filtroRolId != null)
            redirectAttributes.addAttribute("rolId", filtroRolId);
        if (filtroMostrarTodos != null && filtroMostrarTodos)
            redirectAttributes.addAttribute("mostrarTodos", true);

        return "redirect:/web/trabajadores";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Integer id,
            @RequestParam(required = false) Integer rolId,
            @RequestParam(required = false) Boolean mostrarTodos,
            org.springframework.web.servlet.mvc.support.RedirectAttributes redirectAttributes) {

        trabajadorService.eliminar(id);

        if (rolId != null)
            redirectAttributes.addAttribute("rolId", rolId);
        if (mostrarTodos != null && mostrarTodos)
            redirectAttributes.addAttribute("mostrarTodos", true);

        return "redirect:/web/trabajadores";
    }
}
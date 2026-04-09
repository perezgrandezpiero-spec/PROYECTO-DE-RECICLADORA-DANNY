package com.proyecto.Controller;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import com.proyecto.Model.Compra;
import com.proyecto.Model.DetalleCompra;
import com.proyecto.Model.Material;
import com.proyecto.Model.Usuario;
import com.proyecto.Service.CompraService;
import com.proyecto.Service.MaterialService;
import com.proyecto.Service.ProveedorService;
import com.proyecto.Service.UsuarioService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/web/compras")
@SessionAttributes("compra")
public class CompraController {

	private final UsuarioService usuarioService;

	@Autowired
	private CompraService compraService;
	@Autowired
	private ProveedorService proveedorService;
	@Autowired
	private MaterialService materialService;

	CompraController(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}

	// @Autowired
	// private TrabajadorService trabajadorService;

	@GetMapping
	public String listar(@RequestParam(required = false) Integer trabajadorId, Model model, SessionStatus status) {
		status.setComplete();

		List<Compra> lista;
		if (trabajadorId != null) {
			lista = compraService.listarPorTrabajador(trabajadorId);
			model.addAttribute("trabajadorSeleccionado", trabajadorId);
		} else {
			lista = compraService.listarCompras();
		}

		model.addAttribute("compras", lista);
		model.addAttribute("trabajadores", usuarioService.listarTodos());
		return "compras/listaCompras";
	}

	@GetMapping("/nueva")
	public String nueva(Model model) {
		Compra compra = new Compra();
		compra.setTotal(0.0);
		compra.setDetalles(new ArrayList<>());

		model.addAttribute("compra", compra);
		cargarListas(model);
		return "compras/formularioCompra";
	}

	@PostMapping("/agregar-item")
	public String agregarItem(@ModelAttribute Compra compra, @RequestParam(required = false) Integer materialId,
			@RequestParam(required = false) int cantidad, @RequestParam(required = false) Double precio, Model model) {

		if (materialId == null || cantidad == -1 || precio == null) {
			model.addAttribute("error", "Error: Seleccione un material, cantidad y precio validos.");
			cargarListas(model);
			return "compras/formularioCompra";
		}

		boolean existe = false;
		if (compra.getDetalles() == null)
			compra.setDetalles(new ArrayList<>());

		for (DetalleCompra det : compra.getDetalles()) {
			if (det.getMaterial().getId().equals(materialId)) {
				det.setCantidad((det.getCantidad() + cantidad));
				det.setSubtotal(det.getCantidad() * det.getPrecio());
				existe = true;
				break;
			}
		}

		if (!existe) {
			Material material = materialService.buscarPorId(materialId).orElse(null);
			DetalleCompra detalle = new DetalleCompra();
			detalle.setMaterial(material);
			detalle.setCantidad((int) cantidad);
			detalle.setPrecio(precio);
			detalle.setSubtotal(cantidad * precio);
			compra.agregarDetalle(detalle);
		}

		double sumaTotal = compra.getDetalles().stream().mapToDouble(DetalleCompra::getSubtotal).sum();
		compra.setTotal(sumaTotal);

		cargarListas(model);
		return "compras/formularioCompra";
	}

	@GetMapping("/eliminar-item/{index}")
	public String eliminarItem(@ModelAttribute Compra compra, @PathVariable int index, Model model) {
		if (index >= 0 && index < compra.getDetalles().size()) {
			compra.getDetalles().remove(index);

			double sumaTotal = compra.getDetalles().stream().mapToDouble(DetalleCompra::getSubtotal).sum();
			compra.setTotal(sumaTotal);
		}
		cargarListas(model);
		return "compras/formularioCompra";
	}

	@PostMapping("/guardar")
	public String guardar(@ModelAttribute Compra compra, HttpSession session, SessionStatus status) {
		Usuario t = (Usuario) session.getAttribute("usuarioLogueado");
		if (t == null) {
			return "redirect:/login";
		}
		compra.setUsuario(t);

		compraService.guardarCompra(compra);

		status.setComplete();
		return "redirect:/web/compras";
	}

	private void cargarListas(Model model) {
		model.addAttribute("proveedores", proveedorService.listarActivos());
		model.addAttribute("materiales", materialService.listarActivosPorTipo("RESIDUO"));
	}

	@GetMapping("/ver/{id}")
	public String verDetalle(@PathVariable Integer id, Model model) {
		Compra compra = compraService.buscarPorId(id);
		if (compra == null) {
			return "redirect:/web/compras";
		}
		model.addAttribute("compra", compra);
		return "compras/detalleCompra";
	}
}
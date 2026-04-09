package com.proyecto.Controller;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import com.proyecto.Model.DetalleVenta;
import com.proyecto.Model.Material;
import com.proyecto.Model.Usuario;
import com.proyecto.Model.Venta;
import com.proyecto.Service.ClienteService;
import com.proyecto.Service.MaterialService;
import com.proyecto.Service.UsuarioService;
import com.proyecto.Service.VentaService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/web/ventas")
@SessionAttributes("venta")
public class VentaController {

	@Autowired
	private VentaService ventaService;

	@Autowired
	private ClienteService clienteService;

	@Autowired
	private MaterialService materialService;

	@Autowired
	private UsuarioService trabajadorService;

	@GetMapping
	public String listar(@RequestParam(required = false) Integer trabajadorId, Model model, SessionStatus status) {

		status.setComplete();

		List<Venta> lista;
		if (trabajadorId != null) {
			lista = ventaService.listarPorTrabajador(trabajadorId);
			model.addAttribute("trabajadorSeleccionado", trabajadorId);
		} else {
			lista = ventaService.listarVentas();
		}

		model.addAttribute("ventas", lista);
		model.addAttribute("trabajadores", trabajadorService.listarTodos());
		return "ventas/listaVentas";
	}

	@GetMapping("/nueva")
	public String nueva(Model model) {
		Venta venta = new Venta();
		venta.setMontoTotal(0.0);
		venta.setDetalles(new ArrayList<>());

		model.addAttribute("venta", venta);
		cargarListas(model);
		return "ventas/formularioVenta";
	}

	@PostMapping("/agregar-item")
	public String agregarItem(@ModelAttribute Venta venta,

			@RequestParam(required = false) Integer materialId, @RequestParam(required = false) Double cantidad,
			@RequestParam(required = false) Double precio, Model model) {

		if (materialId == null || cantidad == null || precio == null) {
			model.addAttribute("error", "Error: Debe seleccionar un producto, cantidad y precio válidos.");
			cargarListas(model);
			return "ventas/formularioVenta";
		}

		Material producto = materialService.buscarPorId(materialId).orElse(null);

		if (producto.getStock() < cantidad) {
			model.addAttribute("error", "Stock insuficiente. Disponible: " + producto.getStock());
			cargarListas(model);
			return "ventas/formularioVenta";
		}

		boolean existe = false;

		if (venta.getDetalles() == null) {
			venta.setDetalles(new ArrayList<>());
		}

		for (DetalleVenta det : venta.getDetalles()) {
			if (det.getMaterial().getId().equals(materialId)) {

				if (producto.getStock() < (det.getCantidad() + cantidad)) {
					model.addAttribute("error", "Stock insuficiente para sumar esa cantidad adicional.");
					cargarListas(model);
					return "ventas/formularioVenta";
				}

				det.setCantidad(det.getCantidad() + cantidad);
				det.setPrecioUnitario(precio);
				det.setSubtotal(det.getCantidad() * precio);
				existe = true;
				break;
			}
		}

		if (!existe) {
			DetalleVenta detalle = new DetalleVenta();
			detalle.setMaterial(producto);
			detalle.setCantidad(cantidad);
			detalle.setPrecioUnitario(precio);
			detalle.setSubtotal(cantidad * precio);
			venta.agregarDetalle(detalle);
		}

		double sumaTotal = venta.getDetalles().stream().mapToDouble(DetalleVenta::getSubtotal).sum();
		venta.setMontoTotal(sumaTotal);

		cargarListas(model);
		return "ventas/formularioVenta";
	}

	@GetMapping("/eliminar-item/{index}")
	public String eliminarItem(@ModelAttribute Venta venta, @PathVariable int index, Model model) {
		if (index >= 0 && index < venta.getDetalles().size()) {
			venta.getDetalles().remove(index);

			double sumaTotal = venta.getDetalles().stream().mapToDouble(DetalleVenta::getSubtotal).sum();
			venta.setMontoTotal(sumaTotal);
		}
		cargarListas(model);
		return "ventas/formularioVenta";
	}

	@PostMapping("/guardar")
	public String guardar(@ModelAttribute Venta venta, HttpSession session, SessionStatus status, Model model) {
		try {
			Usuario t = (Usuario) session.getAttribute("usuarioLogueado");
			if (t == null) {
				return "redirect:/login";
			}
			venta.setTrabajador(t);

			ventaService.guardarVenta(venta);

			status.setComplete();
			return "redirect:/web/ventas";

		} catch (RuntimeException e) {

			model.addAttribute("error", e.getMessage());
			cargarListas(model);
			return "ventas/formularioVenta";
		}
	}

	@GetMapping("/ver/{id}")
	public String verDetalle(@PathVariable Integer id, Model model) {
		Venta venta = ventaService.buscarPorId(id);
		if (venta == null) {
			return "redirect:/web/ventas";
		}
		model.addAttribute("venta", venta);
		return "ventas/detalleVenta";
	}

	private void cargarListas(Model model) {
		model.addAttribute("clientes", clienteService.listarActivos());

		model.addAttribute("productos", materialService.listarActivosPorTipo("PRODUCTO"));
	}
}

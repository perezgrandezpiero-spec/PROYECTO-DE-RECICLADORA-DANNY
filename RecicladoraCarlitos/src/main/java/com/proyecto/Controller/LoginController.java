package com.proyecto.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.proyecto.DTO.IComprasPorMes;
import com.proyecto.DTO.IVentasPorMes;
import com.proyecto.Model.Material;
import com.proyecto.Model.Usuario;
import com.proyecto.Service.ClienteService;
import com.proyecto.Service.CompraService;
import com.proyecto.Service.MaterialService;
import com.proyecto.Service.ProveedorService;
import com.proyecto.Service.UsuarioService;
import com.proyecto.Service.VentaService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/")
public class LoginController {

	@Autowired
	private ClienteService clienteService;
	@Autowired
	private ProveedorService proveedorService;
	@Autowired
	private MaterialService materialService;
	@Autowired
	private VentaService ventaService;
	@Autowired
	private CompraService compraService;
	//@Autowired
	//private UsuarioService usuarioService;

	@GetMapping
	public String index() {
		return "redirect:/login";
	}

	@GetMapping("/login")
	public String login() {
		return "login";
	}
	/*
	 @PostMapping("/login") public String procesarLogin(@RequestParam String
	 username, @RequestParam String password, HttpSession session, Model model) {
	 Usuario t = usuarioService.validarCredenciales(username, password);
	
	 if (t != null) { session.setAttribute("usuarioLogueado", t); return
	 "redirect:/web/home"; } else { model.addAttribute("error",
	 "Credenciales incorrectas"); return "login"; } }
	 */

	@GetMapping("/web/home")
	public String home(Model model) {

		model.addAttribute("cantClientes", clienteService.contarClientes());
		model.addAttribute("cantProveedores", proveedorService.contarProveedores());
		model.addAttribute("cantProductos", materialService.contarProductos());
		model.addAttribute("cantResiduos", materialService.contarResiduos());
		Double totalVentas = ventaService.obtenerTotalVentas();
		Double totalCompras = compraService.obtenerTotalCompras();
		model.addAttribute("totalVentas", totalVentas != null ? totalVentas : 0.0);
		model.addAttribute("totalCompras", totalCompras != null ? totalCompras : 0.0);

		model.addAttribute("alertasStock", materialService.buscarStockBajo(10.0));

		List<IVentasPorMes> datosVentas = ventaService.obtenerReporteMensual();
		List<IComprasPorMes> datosCompras = compraService.obtenerReporteMensual();

		java.util.TreeMap<String, Double[]> balanceMap = new java.util.TreeMap<>();

		if (datosVentas != null) {
			for (IVentasPorMes v : datosVentas) {

				String key = String.format("%04d-%02d", v.getAnio(), v.getMes());
				balanceMap.putIfAbsent(key, new Double[] { 0.0, 0.0 });
				balanceMap.get(key)[0] = v.getTotal();
			}
		}

		if (datosCompras != null) {
			for (IComprasPorMes c : datosCompras) {
				String key = String.format("%04d-%02d", c.getAnio(), c.getMes());
				balanceMap.putIfAbsent(key, new Double[] { 0.0, 0.0 });
				balanceMap.get(key)[1] = c.getTotal();
			}
		}

		List<String> labels = new ArrayList<>();
		List<Double> dataVentasList = new ArrayList<>();
		List<Double> dataComprasList = new ArrayList<>();

		for (java.util.Map.Entry<String, Double[]> entry : balanceMap.entrySet()) {

			String[] parts = entry.getKey().split("-");
			String mes = parts[1];
			String anio = parts[0];

			try {
				int mesInt = Integer.parseInt(mes);
				labels.add(mesInt + "/" + anio);
			} catch (NumberFormatException e) {
				labels.add(entry.getKey());
			}

			dataVentasList.add(entry.getValue()[0]);
			dataComprasList.add(entry.getValue()[1]);
		}

		model.addAttribute("graficoLabels", labels);
		model.addAttribute("graficoDataVentas", dataVentasList);
		model.addAttribute("graficoDataCompras", dataComprasList);

		List<Material> materiales = materialService.listarActivos();
		Map<String, Double> stockPorCategoria = new HashMap<>();

		for (Material m : materiales) {
			if (m.getTipoMaterial() != null && m.getStock() != null && m.getStock() > 0) {
				String catNombre = m.getTipoMaterial().getNombre();
				Double stockEnKg = 0.0;

				if ("TON".equalsIgnoreCase(m.getUnidad().getDescripcion())) {
					stockEnKg = m.getStock() * 1000.0;
				} else if ("UNIDAD".equalsIgnoreCase(m.getUnidad().getDescripcion())) {
					stockEnKg = m.getStock() * 600.0;
				} else {

					stockEnKg = m.getStock();
				}

				stockPorCategoria.put(catNombre, stockPorCategoria.getOrDefault(catNombre, 0.0) + stockEnKg);
			}
		}

		List<String> inventoryLabels = new ArrayList<>(stockPorCategoria.keySet());
		List<Double> inventoryData = new ArrayList<>(stockPorCategoria.values());

		model.addAttribute("inventoryLabels", inventoryLabels);
		model.addAttribute("inventoryData", inventoryData);

		return "home";
	}

	/*
	 @GetMapping("/logout") public String logout(HttpSession session) {
	 session.invalidate(); return "redirect:/login"; }
	 */
}

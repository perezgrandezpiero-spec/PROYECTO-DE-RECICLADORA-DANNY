package com.proyecto.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.proyecto.Service.*;

@Controller
@RequestMapping("/web/reportes")
public class ReporteController {

	@Autowired
	private JasperService jasperService;
	@Autowired
	private MaterialService materialService;
	@Autowired
	private ClienteService clienteService;
	@Autowired
	private ProveedorService proveedorService;
	@Autowired
	private CompraService compraService;
	@Autowired
	private VentaService ventaService;
	@Autowired
	private UsuarioService trabajadorService;

	@GetMapping("/residuos")
	public ResponseEntity<byte[]> reporteResiduos() {
		return generarPdf(materialService.listarResiduos(), "inventarioResiduos", "ReporteResiduos.pdf");
	}

	@GetMapping("/productos")
	public ResponseEntity<byte[]> reporteProductos() {
		return generarPdf(materialService.listarProductos(), "inventarioProductos", "ReporteProductos.pdf");
	}

	@GetMapping("/clientes")
	public ResponseEntity<byte[]> reporteClientes() {

		return generarPdf(clienteService.listarTodos(), "listaClientes", "ReporteClientes.pdf");
	}

	@GetMapping("/proveedores")
	public ResponseEntity<byte[]> reporteProveedores() {

		return generarPdf(proveedorService.listarTodos(), "listaProveedores", "ReporteProveedores.pdf");
	}

	@GetMapping("/compras")
	public ResponseEntity<byte[]> reporteCompras() {

		return generarPdf(compraService.listarCompras(), "historialCompras", "ReporteCompras.pdf");
	}

	@GetMapping("/ventas")
	public ResponseEntity<byte[]> reporteVentas() {

		return generarPdf(ventaService.listarVentas(), "historialVentas", "ReporteVentas.pdf");
	}

	@GetMapping("/usuarios")
	public ResponseEntity<byte[]> reporteUsuarios() {

		return generarPdf(trabajadorService.listarTodos(), "listaTrabajadores", "ReportePersonal.pdf");
	}

	private ResponseEntity<byte[]> generarPdf(List<?> datos, String nombreJrxml, String nombreSalida) {
		try {
			byte[] reporte = jasperService.generarReporte(datos, nombreJrxml);

			return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + nombreSalida)
					.contentType(MediaType.APPLICATION_PDF).body(reporte);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().build();
		}
	}
}
package com.proyecto.Service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proyecto.DTO.IComprasPorMes;
import com.proyecto.Model.Compra;
import com.proyecto.Model.DetalleCompra;
import com.proyecto.Model.Material;
import com.proyecto.Repository.CompraRepository;
import com.proyecto.Repository.MaterialRepository;

@Service
public class CompraService {

	@Autowired
	private CompraRepository compraRepository;

	@Autowired
	private MaterialRepository materialRepository;

	public List<Compra> listarCompras() {
		return compraRepository.findAllByOrderByFechaDesc();
	}

	public List<Compra> listarPorTrabajador(Integer UserId) {
		return compraRepository.findByUsuarioIdOrderByFechaDesc(UserId);
	}

	public Compra buscarPorId(Integer id) {
		return compraRepository.findById(id).orElse(null);
	}

	public Double obtenerTotalCompras() {
		return compraRepository.sumarComprasTotales();
	}

	@Transactional
	public Compra guardarCompra(Compra compra) {

		if (compra.getFecha() == null) {
			compra.setFecha(LocalDateTime.now());
		}

		if (compra.getCodigo() == null) {
			compra.setCodigo("CP-" + System.currentTimeMillis());
		}

		for (DetalleCompra detalle : compra.getDetalles()) {

			Material material = materialRepository.findById(detalle.getMaterial().getId())
					.orElseThrow(() -> new RuntimeException("Material no encontrado"));

			Double nuevoStock = material.getStock() + detalle.getCantidad();
			material.setStock(nuevoStock);

			materialRepository.save(material);
		}
		return compraRepository.save(compra);
	}

	public List<IComprasPorMes> obtenerReporteMensual() {
		return compraRepository.obtenerComprasPorMes();
	}

}
package com.proyecto.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "detalle_venta")
@AllArgsConstructor
@NoArgsConstructor
public class DetalleVenta {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private double precioUnitario;

	private double subtotal;
	// No tiene venta
	@ManyToOne
	@JoinColumn(name = "venta_id", nullable = false)
	private Venta venta;

	// No tiene material
	@ManyToOne
	@JoinColumn(name = "material_id", nullable = false)
	private Material material;

	// Falta cantidad
	private double cantidad;
}

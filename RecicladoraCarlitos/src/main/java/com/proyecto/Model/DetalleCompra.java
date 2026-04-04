package com.proyecto.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "detalleCompra")
@AllArgsConstructor
@NoArgsConstructor
public class DetalleCompra {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column
	private double peso;
	@Column
	private double precio;
	@Column
	private double subtotal;

	public double calcularSubtotal(double peso, double precio) {
		return this.subtotal = peso * precio;
	}

	public boolean validarPeso(double peso) {
		return false;
	}
}

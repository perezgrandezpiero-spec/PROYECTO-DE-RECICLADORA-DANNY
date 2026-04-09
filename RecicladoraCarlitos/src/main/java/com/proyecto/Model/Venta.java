package com.proyecto.Model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Id;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity // ENTIDAD
@Data // GETTERS Y SETTERS
@Table(name = "venta")
@AllArgsConstructor // new Class(Arg1,Arg2,...,ArgN)
@NoArgsConstructor // new Class()
public class Venta {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column
	private LocalDate fecha;

	@Column
	private double montoTotal; // monto_total
	private String codigo;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_tipo_comprobante", nullable = false)
	private TipoComprobante tipoComprobante;

	// No tiene cliente
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cliente_id", nullable = false)
	private Cliente cliente;

	// No tiene trabajador
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "trabajador_id", nullable = false)
	private Usuario trabajador;

	// No tiene lista de detalles
	@OneToMany(mappedBy = "venta", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<DetalleVenta> detalles = new ArrayList<>();

	public void agregarDetalle(DetalleVenta detalle) {
		detalles.add(detalle);
		detalle.setVenta(this);
	}
}

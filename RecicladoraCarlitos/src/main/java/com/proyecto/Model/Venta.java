package com.proyecto.Model;

import java.time.LocalDate;

import jakarta.persistence.Id;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_tipo_comprobante", nullable = false)
	private TipoComprobante tipoComprobante;
}

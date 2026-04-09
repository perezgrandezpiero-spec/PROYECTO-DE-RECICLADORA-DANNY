package com.proyecto.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
@Data // get y setter
@Table(name = "Material")
@NoArgsConstructor
@AllArgsConstructor
public class Material {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_material")
	private Integer id;
	@Column
	private String nombre;
	@Column
	private Double stock;
	@Column
	private double precio;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_tipo_material", nullable = false)
	private TipoMaterial tipoMaterial;

	private boolean activo = true;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_unidad_medida")
	private UnidadMedida unidad;

}

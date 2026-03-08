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
@Data
@Table(name = "Material")
@NoArgsConstructor
@AllArgsConstructor
public class Material {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "nombre_material")
	private String nombreMaterial;
	@Column
	private double precio;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_tipo_material", nullable = false)
	private TipoMaterial tipoMaterial;

}

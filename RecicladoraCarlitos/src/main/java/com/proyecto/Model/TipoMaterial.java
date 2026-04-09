package com.proyecto.Model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "tipoMaterial")
@NoArgsConstructor
@AllArgsConstructor
public class TipoMaterial {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String nombre;

	private boolean activo = true;
	// No tiene lista de materiales
	@OneToMany(mappedBy = "tipoMaterial", cascade = CascadeType.ALL)
	private List<Material> materiales = new ArrayList<>();
}

package com.proyecto.Model;

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
@Table(name = "usuario")
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String nombre;

	private String apellido;

	private String nombreUsuario;//nombre_usuario

	private String contra;

	private String telefono;
	private String correo;

	private boolean estado;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_rol", nullable = false)
	private Rol rol;
}

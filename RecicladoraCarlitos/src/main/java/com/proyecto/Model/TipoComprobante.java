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

@Entity // ENTIDAD
@Data // GETTERS Y SETTERS
@Table(name = "TipoComprobante")
@AllArgsConstructor // new Class(Arg1,Arg2,...,ArgN)
@NoArgsConstructor // new Class()
public class TipoComprobante {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column
	private String descripcion;
}

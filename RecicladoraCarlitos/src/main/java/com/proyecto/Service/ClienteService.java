package com.proyecto.Service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.Model.Cliente;
import com.proyecto.Repository.ClienteRepository;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;

	public List<Cliente> listarTodos() {
		return clienteRepository.findAll();
	}

	public List<Cliente> listarActivos() {
		return clienteRepository.findByActivoTrue();
	}

	public int contarClientes() {
		return (int) clienteRepository.count();
	}

	public void guardar(Cliente cliente) {
		clienteRepository.save(cliente);
	}

	public Cliente buscarPorId(Integer id) {
		return clienteRepository.findById(id).orElse(null);
	}

	public void eliminar(Integer id) {

		Cliente c = buscarPorId(id);
		if (c != null) {
			c.setActivo(false);
			clienteRepository.save(c);
		}
	}
}
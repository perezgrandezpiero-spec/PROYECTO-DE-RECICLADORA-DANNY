package com.proyecto.Service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reciclaje.model.Proveedor;
import com.reciclaje.repository.ProveedorRepository;

@Service
public class ProveedorService {

    @Autowired
    private ProveedorRepository proveedorRepository;

    public List<Proveedor> listarTodos() {
        return proveedorRepository.findAll();
    }

    public List<Proveedor> listarActivos() {
        return proveedorRepository.findByActivoTrue();
    }

    public void guardar(Proveedor proveedor) {
        proveedorRepository.save(proveedor);
    }

    public Proveedor buscarPorId(Integer id) {
        return proveedorRepository.findById(id).orElse(null);
    }

  
    public long contarProveedores() {
        return proveedorRepository.count(); 
    }

    public void eliminar(Integer id) {
        Proveedor c = buscarPorId(id);
        if (c != null) {
            c.setActivo(false); 
            proveedorRepository.save(c); 
        }
    }
}
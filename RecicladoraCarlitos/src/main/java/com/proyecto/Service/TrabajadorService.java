package com.proyecto.Service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.reciclaje.model.Rol;
import com.reciclaje.model.Trabajador;
import com.reciclaje.repository.RolRepository;
import com.reciclaje.repository.TrabajadorRepository;

@Service
public class TrabajadorService {

    @Autowired
    private TrabajadorRepository trabajadorRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<Trabajador> listarTodos() {
        return trabajadorRepository.findAll();
    }

    public List<Trabajador> listarActivos() {
        return trabajadorRepository.findByActivoTrue();
    }

    public List<Trabajador> listarPorRol(Integer rolId) {
        return trabajadorRepository.findByRolId(rolId);
    }

    public List<Trabajador> listarActivosPorRol(Integer rolId) {
        return trabajadorRepository.findByRolIdAndActivoTrue(rolId);
    }

    public void guardar(Trabajador t) {
        if (t.getId() != null && (t.getPass() == null || t.getPass().isEmpty())) {
            // Es edición y no se ingresó contraseña: Recuperar la actual
            trabajadorRepository.findById(t.getId()).ifPresent(existing -> {
                t.setPass(existing.getPass());
            });
        } else {
            // Nuevo usuario o cambio de contraseña: Encriptar
            t.setPass(passwordEncoder.encode(t.getPass()));
        }
        trabajadorRepository.save(t);
    }

    public Trabajador buscarPorId(Integer id) {
        return trabajadorRepository.findById(id).orElse(null);
    }

    public void eliminar(Integer id) {
        Trabajador t = buscarPorId(id);
        if (t != null) {
            t.setActivo(false);
            trabajadorRepository.save(t);
        }
    }

    public Trabajador validarCredenciales(String nombre, String pass) {
        Trabajador t = trabajadorRepository.findByUsername(nombre);

        if (t != null && t.isActivo() && passwordEncoder.matches(pass, t.getPass())) {
            return t;
        }
        return null;
    }

    public List<Rol> listarRoles() {
        return rolRepository.findAll();
    }
}
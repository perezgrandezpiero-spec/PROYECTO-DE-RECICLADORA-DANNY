package com.proyecto.Service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.reciclaje.dto.IVentasPorMes;
import com.reciclaje.model.DetalleVenta;
import com.reciclaje.model.Material;
import com.reciclaje.model.Venta;
import com.reciclaje.repository.MaterialRepository;
import com.reciclaje.repository.VentaRepository;

@Service
public class VentaService {

    @Autowired
    private VentaRepository ventaRepository;

    @Autowired
    private MaterialRepository materialRepository;

    public List<Venta> listarVentas() {
        return ventaRepository.findAllByOrderByFechaDesc();
    }

    public List<Venta> listarPorTrabajador(Integer trabajadorId) {
        return ventaRepository.findByTrabajadorIdOrderByFechaDesc(trabajadorId);
    }

    public Venta buscarPorId(Integer id) {
        return ventaRepository.findById(id).orElse(null);
    }

  

   
    public Double obtenerTotalVentas() {
        return ventaRepository.sumarVentasTotales();
    }

   
    public List<IVentasPorMes> obtenerReporteMensual() {
        return ventaRepository.obtenerVentasPorMes();
    }

    @Transactional
    public void guardarVenta(Venta venta) {

       
        if (venta.getFecha() == null) {
            venta.setFecha(LocalDateTime.now());
        }

       
        if (venta.getCodigo() == null) {
            venta.setCodigo("VN-" + System.currentTimeMillis());
        }

      
        for (DetalleVenta detalle : venta.getDetalles()) {

            
            Material producto = materialRepository.findById(detalle.getMaterial().getId())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

            
            if (producto.getStock() < detalle.getCantidad()) {
                throw new RuntimeException("Stock insuficiente para el producto: " + producto.getNombre());
            }

           
            Double nuevoStock = producto.getStock() - detalle.getCantidad();
            producto.setStock(nuevoStock);

            
            materialRepository.save(producto);
        }

      
        ventaRepository.save(venta);
    }
}
package com.proyecto.Repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.reciclaje.model.Venta;
import com.reciclaje.dto.IVentasPorMes;

@Repository
public interface VentaRepository extends JpaRepository<Venta, Integer> {
    
    List<Venta> findAllByOrderByFechaDesc();

    List<Venta> findByTrabajadorIdOrderByFechaDesc(Integer trabajadorId);

    @Query(value = "CALL sp_sumar_ventas_totales()", nativeQuery = true)
    Double sumarVentasTotales();

   
    @Query(value = "CALL sp_obtener_ventas_por_mes()", nativeQuery = true)
    List<IVentasPorMes> obtenerVentasPorMes();
}
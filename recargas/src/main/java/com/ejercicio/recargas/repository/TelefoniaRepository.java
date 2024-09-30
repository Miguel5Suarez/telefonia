package com.ejercicio.recargas.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ejercicio.recargas.dto.VentasDto;
import com.ejercicio.recargas.entity.TelefoniaEntity;

public interface TelefoniaRepository extends JpaRepository<TelefoniaEntity, Long>{
	
	@Query(value = "select h.*"  
			  + " from HISTORICO_VENTAS h, CARRIER c, PAQUETES_TELEFONICOS p"
			  + " where h.id_carrier = c.id"
			  + " and h.id_paquete = p.id"
			  + " and h.fecha_transaccion = CURRENT_DATE();"
			  , nativeQuery = true)
	List<TelefoniaEntity> totalVentas();
	
	@Query(value = "select h.*"
			  + " from HISTORICO_VENTAS h, CARRIER c, PAQUETES_TELEFONICOS p"
			  + " where h.id_carrier = c.id"
			  + " and h.id_paquete = p.id"
			  + " and h.fecha_transaccion BETWEEN :fechaInicio AND :fechaFin;"
			  , nativeQuery = true)
	List<TelefoniaEntity> ventasPorRango(@Param("fechaInicio") String fechaInicio, 
			@Param("fechaFin") String fechaFin);
	
	@Query("select new com.ejercicio.recargas.dto.VentasDto(sum(p.valor), count(h.id), c.nombre, p.valor) "
		      + "from TelefoniaEntity h "
		      + "join CarrierEntity c on h.idCarrier = c.id "
		      + "join PaquetesEntity p on h.idPaquete = p.id "
		      + "where c.nombre = :carrier "
		      + "and h.fechaTransaccion = :fecha "
		      + "and p.valor = :monto "
		      + "group by p.valor")
	List<VentasDto> ventasPorCarrier(@Param("carrier") String carrier, @Param("fecha") String fecha, @Param("monto") int monto);
	
	@Query("select new com.ejercicio.recargas.dto.VentasDto(sum(p.valor), count(h.id), c.nombre, p.valor) "
		      + "from TelefoniaEntity h "
		      + "join CarrierEntity c on h.idCarrier = c.id "
		      + "join PaquetesEntity p on h.idPaquete = p.id "
		      + "where p.valor = :monto "
		      + "and h.fechaTransaccion = :fecha "
		      + "group by c.nombre")
	List<VentasDto> ventasPorMonto(@Param("monto") int monto, @Param("fecha") String fecha);
	
	@Query("select new com.ejercicio.recargas.dto.VentasDto(sum(p.valor), count(h.id), c.nombre, p.valor) "
		      + "from TelefoniaEntity h "
		      + "join CarrierEntity c on h.idCarrier = c.id "
		      + "join PaquetesEntity p on h.idPaquete = p.id "
		      + "where h.numeroTelefonico = :numeroTelefono "
		      + "and h.fechaTransaccion BETWEEN :fechaInicio AND :fechaFin "
		      + "group by h.numeroTelefonico, c.nombre, p.valor")
	List<VentasDto> ventasPorTelefono(int numeroTelefono, String fechaInicio, String fechaFin);
	
	@Query(value = "select h.fecha"  
			  + " from HISTORICO_VENTAS h"
			  + " where h.id_paquete = :paquete"
			  + " and h.numero_telefonico =:numero"
			  + " order by h.fecha_Transaccion desc"
			  + " LIMIT 1;"
			  , nativeQuery = true)
	Date totalVentasFiltro(@Param("numero") String numero, @Param("paquete") int paquete);
}

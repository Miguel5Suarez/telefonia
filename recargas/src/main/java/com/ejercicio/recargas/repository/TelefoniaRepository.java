package com.ejercicio.recargas.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ejercicio.recargas.dto.VentasDto;
import com.ejercicio.recargas.entity.TelefoniaEntity;

public interface TelefoniaRepository extends JpaRepository<TelefoniaEntity, Long>{
	
	/**
	 * Query para obtener el historico de total de ventas
	 * @return List<TelefoniaEntity>
	 */
	@Query(value = "select h.*"  
			  + " from HISTORICO_VENTAS h, CARRIER c, PAQUETES_TELEFONICOS p"
			  + " where h.id_carrier = c.id"
			  + " and h.id_paquete = p.id"
			  + " and DATE(h.fecha_transaccion) = CURRENT_DATE();"
			  , nativeQuery = true)
	List<TelefoniaEntity> totalVentas();
	
	/**
	 * Query para obtener el historico de total de ventas por rango de fechas
	 * @param fechaInicio
	 * @param fechaFin
	 * @return List<TelefoniaEntity>
	 */
	@Query(value = "select h.*"
			  + " from HISTORICO_VENTAS h, CARRIER c, PAQUETES_TELEFONICOS p"
			  + " where h.id_carrier = c.id"
			  + " and h.id_paquete = p.id"
			  + " and DATE(h.fecha_transaccion) BETWEEN :fechaInicio AND :fechaFin;"
			  , nativeQuery = true)
	List<TelefoniaEntity> ventasPorRango(@Param("fechaInicio") String fechaInicio, 
			@Param("fechaFin") String fechaFin);
	
	/**
	 * Query para validar ¿Cuántos paquetes de $100 han sido vendidos por Carrier?
	 * @param carrier
	 * @param monto
	 * @return List<VentasDto>
	 */
	@Query("select new com.ejercicio.recargas.dto.VentasDto(sum(p.valor), count(h.id), c.nombre, p.valor) "
		      + "from TelefoniaEntity h "
		      + "join CarrierEntity c on h.idCarrier = c.id "
		      + "join PaquetesEntity p on h.idPaquete = p.id "
		      + "where c.nombre = :carrier "
		      + "and DATE(h.fechaTransaccion) = CURRENT_DATE() "
		      + "and p.valor = :monto "
		      + "group by p.valor")
	List<VentasDto> ventasPorCarrier(@Param("carrier") String carrier, @Param("monto") int monto);
	
	/**
	 * Query para obtener el total de ventas por monto
	 * @param monto
	 * @return List<VentasDto>
	 */
	@Query("select new com.ejercicio.recargas.dto.VentasDto(sum(p.valor), count(h.id), c.nombre, p.valor) "
		      + "from TelefoniaEntity h "
		      + "join CarrierEntity c on h.idCarrier = c.id "
		      + "join PaquetesEntity p on h.idPaquete = p.id "
		      + "where p.valor = :monto "
		      + "and DATE(h.fechaTransaccion) = CURRENT_DATE() "
		      + "group by c.nombre")
	List<VentasDto> ventasPorMonto(@Param("monto") int monto);
	
	/**
	 * Query para validar ¿Cuántos paquetes ha comprado un número en particular de teléfono en un determinado periodo de tiempo?
	 * @param numeroTelefono
	 * @param fechaInicio
	 * @param fechaFin
	 * @return List<VentasDto>
	 */
	@Query("select new com.ejercicio.recargas.dto.VentasDto(sum(p.valor), count(h.id), c.nombre, p.valor) "
		      + "from TelefoniaEntity h "
		      + "join CarrierEntity c on h.idCarrier = c.id "
		      + "join PaquetesEntity p on h.idPaquete = p.id "
		      + "where h.numeroTelefonico = :numeroTelefono "
		      + "and DATE(h.fechaTransaccion) BETWEEN :fechaInicio AND :fechaFin "
		      + "group by h.numeroTelefonico, c.nombre, p.valor")
	List<VentasDto> ventasPorTelefono(@Param("numeroTelefono") int numeroTelefono, @Param("fechaInicio") LocalDate fechaInicio, @Param("fechaFin") LocalDate fechaFin);
	
	/**
	 * Query para obtener la ultima fecha de transaccion por medio de número y paquete telefonico
	 * @param numero
	 * @param paquete
	 * @return List<VentasDto>
	 */
	@Query(value = "select h.fecha_transaccion"  
			  + " from HISTORICO_VENTAS h"
			  + " where h.id_paquete = :paquete"
			  + " and h.numero_telefonico =:numero"
			  + " order by h.fecha_Transaccion desc"
			  + " LIMIT 1;"
			  , nativeQuery = true)
	String totalVentasFiltro(@Param("numero") String numero, @Param("paquete") int paquete);

	/**
	 * Query para obtener el total de ventas
	 * @return List<VentasDto>
	 */
	@Query("select new com.ejercicio.recargas.dto.VentasDto(sum(p.valor), count(h.id), c.nombre, p.valor) "
		      + "from TelefoniaEntity h "
		      + "join CarrierEntity c on h.idCarrier = c.id "
		      + "join PaquetesEntity p on h.idPaquete = p.id "
		      + "where DATE(h.fechaTransaccion) = CURRENT_DATE() "
		      + "group by c.nombre, p.valor")
	List<VentasDto> ventasTotal();
}

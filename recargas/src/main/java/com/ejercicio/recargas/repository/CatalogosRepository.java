package com.ejercicio.recargas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ejercicio.recargas.entity.CarrierEntity;
import com.ejercicio.recargas.entity.PaquetesEntity;

@Repository
public interface CatalogosRepository extends JpaRepository<CarrierEntity, Long> {
	@Query("select p from PaquetesEntity p ")
	List<PaquetesEntity> getPaquetes();

	@Query("select p.id from CarrierEntity p where p.nombre =:nombre ")
	Integer getIdCarrier(@Param("nombre") String nombre);

	@Query("select p.id from PaquetesEntity p where p.valor =:monto ")
	Integer getIdPaquete(@Param("monto") int monto);

}

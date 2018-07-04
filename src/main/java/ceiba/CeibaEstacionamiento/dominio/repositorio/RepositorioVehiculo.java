package ceiba.CeibaEstacionamiento.dominio.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ceiba.CeibaEstacionamiento.modelo.ModeloVehiculo;

@Repository
public interface RepositorioVehiculo extends JpaRepository<ModeloVehiculo, String>{
	List<ModeloVehiculo> findByTipo(String name);
	//ModeloVehiculo findByPlaca(String name);
}

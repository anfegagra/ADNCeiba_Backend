package ceiba.CeibaEstacionamiento.controlador;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ceiba.CeibaEstacionamiento.dominio.Parqueadero;
import ceiba.CeibaEstacionamiento.dominio.Vehiculo;
import ceiba.CeibaEstacionamiento.dominio.repositorio.RepositorioVehiculo;
import ceiba.CeibaEstacionamiento.modelo.ModeloVehiculo;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@Service
@RestController
@RequestMapping("/ceiba")
public class Crud {

	public static final String REGISTRO_EXITOSO = "Se registro el ingreso del vehiculo exitosamente";
	public static final String REGISTRO_FALLIDO = "Registro fallido. No hay celdas disponibles";
	public static final String VEHICULO_YA_INGRESADO = "Registro fallido. El vehiculo ya esta en el parqueadero";

	@Autowired
	RepositorioVehiculo repositorioVehiculo;

	public Vehiculo registrarVehiculo(Vehiculo vehiculo, Parqueadero parqueadero) {
		ModeloVehiculo modeloVehiculo = new ModeloVehiculo(vehiculo.getPlaca(), vehiculo.getTipo(),
				vehiculo.getCilindraje(), vehiculo.getEstado());
		ModeloVehiculo resultadoInsercion = null;
		System.out.println(vehiculo.getPlaca());

		if(obtenerVehiculoPorPlaca(modeloVehiculo.getPlaca()) != null) {
			
			ModeloVehiculo vehiculoActualizado = obtenerVehiculoPorPlaca(modeloVehiculo.getPlaca());
			if(vehiculoActualizado.getEstado().equals("Inactivo")){
				vehiculoActualizado.setEstado("Activo");
				DateTime dt = new DateTime();
				vehiculoActualizado.setFechaIngreso(dt.toDate());
				resultadoInsercion = repositorioVehiculo.save(vehiculoActualizado);
				return convertirADominio(resultadoInsercion);
			} else {
				System.out.println("ya existe vehiculo");
				return null;
			}			
		} else {
			resultadoInsercion = repositorioVehiculo.save(modeloVehiculo);
			return convertirADominio(resultadoInsercion);
		}
	}
	
	public Vehiculo registrarSalida(String placa, Parqueadero parqueadero) {
		
		ModeloVehiculo resultadoSalida = null;
		
		if(obtenerVehiculoPorPlaca(placa) != null) {
			ModeloVehiculo modeloVehiculo = obtenerVehiculoPorPlaca(placa);
			if(modeloVehiculo.getEstado().equals("Inactivo")){
				System.out.println("El vehiculo no se encuentra en el parqueadero");
				return null;				
			} else {
				System.out.println("Se registro la salida del vehiculo");				
				modeloVehiculo.setEstado("Inactivo");
				resultadoSalida = repositorioVehiculo.save(modeloVehiculo);				
				
				if(modeloVehiculo.getPlaca().equals("C")) {
					parqueadero.setCeldasDisponiblesCarro(parqueadero.getCeldasDisponiblesCarro()+1);
				} else {
					parqueadero.setCeldasDisponiblesMoto(parqueadero.getCeldasDisponiblesMoto()+1);
				}				
				System.out.println("vehiculo salida = " + resultadoSalida.getPlaca());
				System.out.println("vehiculo salida fecha bd = " + resultadoSalida.getFechaIngreso());
				return convertirADominio(resultadoSalida);			
			}			
		} else {
			return null;
		}
	}
	
	public List<Vehiculo> consultarVehiculosActivos(){
		List<ModeloVehiculo> lista = repositorioVehiculo.findAll();
		List<Vehiculo> listaVehiculos = new ArrayList<>();
		Vehiculo vehiculo = null;
		if(!lista.isEmpty()) {
			for(int i=0;i<lista.size();i++){
				Date fechaIngreso = lista.get(i).getFechaIngreso();
				SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
				String fecha = sdf.format(fechaIngreso);
				vehiculo = new Vehiculo(lista.get(i).getPlaca(), lista.get(i).getTipo(), 
						lista.get(i).getCilindraje(), lista.get(i).getEstado(), lista.get(i).getFechaIngreso(), fecha);
				if(validarEstado(vehiculo) != null){
					listaVehiculos.add(vehiculo);
				}								
			}
		}
		return listaVehiculos;
	}
	
	public Vehiculo validarEstado(Vehiculo vehiculo){
		if(vehiculo.getEstado().equals("Activo")){
			return vehiculo;
		} else {
			return null;
		}
	}

	public int obtenerCantidadCeldasDisponibles(String tipoVehiculo) {

		List<ModeloVehiculo> lista = obtenerListaActivos(tipoVehiculo);

		// System.out.println("Tamano de la lista: " +
		// obtenerVehiculoPorTipo(tipoVehiculo).size());
		int cantidad = 0;

		for (ModeloVehiculo listaFinal : lista) {
			// System.out.println(listaFinal);

			if (listaFinal.getEstado().equals("Activo")) {
				cantidad += 1;
			}
		}

		// System.out.println("Cantidad en crud: " + cantidad);
		return cantidad;

	}

	public List<ModeloVehiculo> obtenerListaActivos(String tipo) {

		return repositorioVehiculo.findByTipo(tipo);
	}

	// Obtener vehiculos por tipo - GET
	@GetMapping("/vehiculos/tipo/{tipo}")
	public List<ModeloVehiculo> obtenerVehiculoPorTipo(@PathVariable(value = "tipo") String tipo) {
		List<ModeloVehiculo> lista = repositorioVehiculo.findByTipo(tipo);
		int cantidad = 0;
		for (ModeloVehiculo listaFinal : lista) {			
			if (listaFinal.getEstado().equals("Activo")) {
				cantidad += 1;
			}
		}
		return repositorioVehiculo.findByTipo(tipo);
	}

	public ModeloVehiculo obtenerVehiculoPorPlaca(@PathVariable(value = "id") String placa) {
		return repositorioVehiculo.findById(placa).orElse(null);		
	}
	
	// Obtener un solo vehiculo por placa - GET
	@GetMapping("/vehiculos/{id}")
	public Vehiculo consultarPorPlaca(@PathVariable(value = "id") String placa) {
		ModeloVehiculo resultadoBusqueda = repositorioVehiculo.findById(placa).orElse(null);
		if(resultadoBusqueda.getEstado().equals("Inactivo")){
			return null;
		}
		return convertirADominio(resultadoBusqueda);		
	}

	public boolean validarCeldasDisponiblesMoto(Vehiculo vehiculo, Parqueadero parqueadero) {
		int ocupadas = obtenerCantidadCeldasDisponibles(vehiculo.getTipo());
		System.out.println(
				"ocupadas antes de ingresar: " + ocupadas + "   -----  " + parqueadero.getCeldasDisponiblesMoto());

		int totalDisponibles;
		if (parqueadero.getCeldasDisponiblesMoto() == 10) {
			totalDisponibles = (parqueadero.getCeldasDisponiblesMoto() - ocupadas);
		} else {
			totalDisponibles = parqueadero.getCeldasDisponiblesMoto();
		}
		actualizarCeldasDisponibles(totalDisponibles, vehiculo.getTipo(), parqueadero);
		return totalDisponibles > 0;
	}

	public boolean validarCeldasDisponiblesCarro(Vehiculo vehiculo, Parqueadero parqueadero) {
		int ocupadas = obtenerCantidadCeldasDisponibles(vehiculo.getTipo());
		System.out.println(
				"ocupadas antes de ingresar: " + ocupadas + "   -----  " + parqueadero.getCeldasDisponiblesCarro());

		int totalDisponibles;
		if (parqueadero.getCeldasDisponiblesCarro() == 20) {
			totalDisponibles = (parqueadero.getCeldasDisponiblesCarro() - ocupadas);
		} else {
			totalDisponibles = parqueadero.getCeldasDisponiblesCarro();
		}
		actualizarCeldasDisponibles(totalDisponibles, vehiculo.getTipo(), parqueadero);
		return totalDisponibles > 0;
	}

	public void actualizarCeldasDisponibles(int totalDisponibles, String tipoVehiculo, Parqueadero parqueadero) {
		if (totalDisponibles > 0) {
			if (tipoVehiculo.equals("C")) {
				parqueadero.setCeldasDisponiblesCarro(totalDisponibles - 1);
			} else {
				parqueadero.setCeldasDisponiblesMoto(totalDisponibles - 1);
			}
		}
	}

	public Vehiculo convertirADominio(ModeloVehiculo modeloVehiculo) {
		Date fechaIngreso = modeloVehiculo.getFechaIngreso();
		SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
		String fecha = sdf.format(fechaIngreso).toString();
		Vehiculo vehiculo = null;
		if (modeloVehiculo != null) {
			vehiculo = new Vehiculo(modeloVehiculo.getPlaca(), modeloVehiculo.getTipo(),
					modeloVehiculo.getCilindraje(), modeloVehiculo.getEstado(), modeloVehiculo.getFechaIngreso(), fecha);
		}
		return vehiculo;
	}
}
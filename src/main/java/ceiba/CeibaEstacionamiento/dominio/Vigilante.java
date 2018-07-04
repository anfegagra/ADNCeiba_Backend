package ceiba.CeibaEstacionamiento.dominio;

import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ceiba.CeibaEstacionamiento.controlador.Crud;

@Service
public class Vigilante {

	public static final String VALIDACION_PLACA_CARRO = "Verifique la placa del carro";
	public static final String VALIDACION_CAMPOS_MOTO = "Verifique el cilindraje o placa de la moto";
	public static final String VALIDACION_PLACA_MOTO = "Verifique la placa de la moto";

	@Autowired
	Crud crud;
	
	@Autowired
	Fecha fecha;
	
	@Autowired
	Validacion validacion;
	
	@Autowired
	Cobro cobro;
	
	public Vigilante(){
		
	}
	
	public Vigilante(Validacion validacion){
		this.validacion = validacion;
	}
	
	public Vigilante(Validacion validacion, Crud crud){
		this.validacion = validacion;
		this.crud = crud;
	}
	
	public Vigilante(Crud crud){
		this.crud = crud;
	}
	
	public Vigilante(Crud crud, Fecha fecha, Cobro cobro){
		this.crud = crud;
		this.fecha = fecha;
		this.cobro = cobro;
	}

	public Vehiculo registrarIngresoVehiculo(Vehiculo vehiculo, Parqueadero parqueadero) {
		Vehiculo vehiculoAIngresar = null;
		if (vehiculo.getTipo().equals("C")) {
			System.out.println("Nuevo carro");
			vehiculoAIngresar = new Carro(vehiculo.getPlaca(), vehiculo.getTipo(), vehiculo.getCilindraje());
		} else {
			System.out.println("Nueva moto");
			vehiculoAIngresar = new Moto(vehiculo.getPlaca(), vehiculo.getTipo(), vehiculo.getCilindraje());
		}
		return hacerValidaciones(vehiculoAIngresar, parqueadero);
	}

	public Vehiculo hacerValidaciones(Vehiculo v, Parqueadero p) {
		Vehiculo vehiculo = null;
		String placaActualizada = v.getPlaca().toUpperCase();
		System.out.println(v.getPlaca());
		v.setPlaca(placaActualizada);
		System.out.println(v.getPlaca());
		//boolean res = validacion.esPlacaValida(v.getPlaca());
		if (validacion.esPlacaValida(v.getPlaca())){
			if (v.getTipo().equals("C") && crud.validarCeldasDisponiblesCarro(v, p)) {
				System.out.println("entro");
				vehiculo = crud.registrarVehiculo(v, p);

			} else if (crud.validarCeldasDisponiblesMoto(v, p)) {
				vehiculo = crud.registrarVehiculo(v, p);
			}
		}
		return vehiculo;
	}
	
	public double registrarSalidaVehiculo(String placa, Parqueadero parqueadero){
		Vehiculo vehiculoASalir = crud.registrarSalida(placa, parqueadero);
		double totalAPagar = 0;
		if(vehiculoASalir != null) {
			Date fechaBD = vehiculoASalir.getFechaIngreso();
			DateTime fechaInicial = fecha.obtenerFechaEntrada(fechaBD);
			DateTime fechaFinal = fecha.obtenerFechaActual();
			Duration duracionParqueo = fecha.obtenerDuracionParqueo(fechaInicial, fechaFinal);
			System.out.println("Duracion parqueo = " + duracionParqueo);
			//System.out.println("cantidad minutos: " + duracionParqueo.getStandardMinutes());
			if(vehiculoASalir.getTipo().equals("C")) {
				totalAPagar = cobro.registrarSalidaCarro(duracionParqueo);
			} else {
				totalAPagar = cobro.registrarSalidaMoto(duracionParqueo);
				totalAPagar = (totalAPagar!=0)?totalAPagar+cobro.calcularCobroCilindraje(vehiculoASalir):totalAPagar;
			}
			return totalAPagar;
		} else {
			return totalAPagar;
		}
	}
	
	public List<Vehiculo> consultarVehiculos(){
		return crud.consultarVehiculosActivos();
	}	
}

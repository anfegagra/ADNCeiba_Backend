package ceiba.CeibaEstacionamiento.dominio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ceiba.CeibaEstacionamiento.controlador.Crud;

@Service
public class Parqueadero {

	private int celdasDisponiblesCarro = 20;
	private int celdasDisponiblesMoto = 10;
	//private Vehiculo vehiculo;
	
	@Autowired
	private Crud crud;
	
	public Parqueadero() {
		
	}
	
	public int getCeldasDisponiblesCarro() {
		return celdasDisponiblesCarro;
	}
	public void setCeldasDisponiblesCarro(int celdasDisponiblesCarro) {
		this.celdasDisponiblesCarro = celdasDisponiblesCarro;
	}
	public int getCeldasDisponiblesMoto() {
		return celdasDisponiblesMoto;
	}
	public void setCeldasDisponiblesMoto(int celdasDisponiblesMoto) {
		this.celdasDisponiblesMoto = celdasDisponiblesMoto;
	}
	
	/*public boolean hayCeldaDisponible(String tipoVehiculo) {
		
		if(tipoVehiculo.equals("C")){
			/*if(celdasDisponiblesCarro > 0){
				System.out.println("Hay " + celdasDisponiblesCarro + " celdas de Carro disponibles");
				return true;
			}else{
				System.out.println("No hay celdas de carro disponibles");
				return false;
			}
			int ocupadas = crud.obtenerCantidadCeldasDisponibles(tipoVehiculo);
			System.out.println("ocupadas: " + ocupadas);
			int totalDisponibles = (celdasDisponiblesCarro - ocupadas);
			
			if(totalDisponibles > 0){
				//System.out.println("Hay " + (totalDisponibles) + " celdas de Carro disponibles");
				return true;
			}else{
				//System.out.println("No hay celdas de carro disponibles");
				return false;
			}
			
		}else{
			/*if(celdasDisponiblesMoto > 0){
				System.out.println("Hay " + celdasDisponiblesMoto + " celdas de Moto disponibles");
				return true;
			}else{
				System.out.println("No hay celdas de moto disponibles");
				return false;
			}
			
			int ocupadas = crud.obtenerCantidadCeldasDisponibles(tipoVehiculo);
			System.out.println("ocupadas: " + ocupadas);
			int totalDisponibles = (celdasDisponiblesMoto - ocupadas);
			
			if(totalDisponibles > 0){
				//System.out.println("Hay " + (totalDisponibles) + " celdas de Moto disponibles");
				return true;
			}else{
				//System.out.println("No hay celdas de moto disponibles");
				return false;
			}
		}
		
	}*/
}

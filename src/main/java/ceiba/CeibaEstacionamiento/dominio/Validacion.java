package ceiba.CeibaEstacionamiento.dominio;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class Validacion {

	public Validacion(){
		
	}
	
	@Autowired
	Fecha fecha;
	
	public Validacion(Fecha fecha){
		this.fecha = fecha;
	}
	
	public boolean esPlacaValida(String placa) {
		String primeraLetraPlaca = placa.substring(0, 1);
		int dia = fecha.obtenerDia();
		System.out.println(dia);
		if (primeraLetraPlaca.equals("A")) {
			if (dia == 7 || dia == 1) {
				return true;
			}
			return false;
		} else {
			return true;
		}
	}
}

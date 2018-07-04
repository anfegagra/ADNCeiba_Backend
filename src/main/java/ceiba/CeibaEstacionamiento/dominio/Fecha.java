package ceiba.CeibaEstacionamiento.dominio;

import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Fecha {	
	
	public Fecha(){
		
	}
	
	public DateTime obtenerFechaEntrada(Date fechaBD){
		DateTime fechaEntrada = new DateTime(fechaBD);
		return fechaEntrada;
	}
	
	public DateTime obtenerFechaActual(){
		DateTime fechaActual = new DateTime();
		return fechaActual;
	}
	
	public Duration obtenerDuracionParqueo(DateTime fechaIngreso, DateTime fechaSalida){
		Duration duracionParqueo = new Duration(fechaIngreso, fechaSalida);
		return duracionParqueo;
	}
		
	public int obtenerDia(){
		DateTime date = new DateTime();
		int dia = date.getDayOfWeek();
		return dia;
	}
	
	/*public int obtenerHora(){
		DateTime date = new DateTime();
		int hora = date.getHourOfDay();
		return hora;
	}
	
	public int obtenerMinutos(){
		DateTime date = new DateTime();
		int minuto = date.getMinuteOfDay();
		return minuto;
	}
	
	public int obtenerSegundos(){
		DateTime date = new DateTime();
		int segundo = date.getSecondOfDay();
		return segundo;
	}*/
	
}

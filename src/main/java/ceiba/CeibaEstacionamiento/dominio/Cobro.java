package ceiba.CeibaEstacionamiento.dominio;

import java.util.Date;

import org.joda.time.Duration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Cobro {
	
	public static final int MINUTOS_EN_NUEVE_HORAS = 540;
	public static final int MINUTOS_DIA = 1440;
	
	private int valorHoraCarro = 1000;
	private int valorDiaCarro = 8000;
	private int valorHoraMoto = 500;
	private int valorDiaMoto = 4000;
	private int valorAdicionalMoto = 2000;
	
	public Cobro(){
		
	}
	
	public double registrarSalidaCarro(Duration duracionParqueo) {
		if(obtenerMinutos(duracionParqueo) < MINUTOS_EN_NUEVE_HORAS){
			System.out.println("Horas < 9");
			return calcularCobroMenorANueveHorasCarro(duracionParqueo);
		} else {
			long cantidadDias = obtenerDias(duracionParqueo);
			System.out.println("cantidad de dias: " + cantidadDias);
			if(cantidadDias > 0) {			
				System.out.println("Dias > 0");
				return calcularCobroDiasMayorACeroCarro(duracionParqueo, cantidadDias);			
			} else {
				System.out.println("Dias = 0 y horas >= 9, entonces cobro el dia");
				return valorDiaCarro;
			}
		}
	}	
	
	public double calcularCobroMenorANueveHorasCarro(Duration duracionParqueo){
		int cantidadHoras = (int)(Math.ceil((float)obtenerMinutos(duracionParqueo)/60));
		System.out.println(cantidadHoras);
		return valorHoraCarro*cantidadHoras;
	}
	
	public double calcularCobroDiasMayorACeroCarro(Duration duracionParqueo, long cantidadDias){
		//long cantidadMinutosUltimoDia = obtenerMinutos(duracionParqueo)-cantidadDias*MINUTOS_DIA;
		int cantidadHorasUlitmoDia = Math.abs((int)(Math.ceil((float)(obtenerMinutos(duracionParqueo)-cantidadDias*MINUTOS_DIA)/60)));
		System.out.println(obtenerMinutos(duracionParqueo));
		System.out.println(cantidadDias*MINUTOS_DIA);
		//System.out.println(cantidadMinutosUltimoDia);
		System.out.println(cantidadHorasUlitmoDia);
		if(cantidadHorasUlitmoDia >= 9){
			cantidadHorasUlitmoDia = 0;
			cantidadDias = cantidadDias+1;							
		}
		return valorDiaCarro*cantidadDias + valorHoraCarro*cantidadHorasUlitmoDia;	
	}
	
	public double registrarSalidaMoto(Duration duracionParqueo){	
		if(obtenerMinutos(duracionParqueo) < MINUTOS_EN_NUEVE_HORAS){			
			return calcularCobroMenorANueveHorasMoto(duracionParqueo);
		} else {
			long cantidadDias= obtenerDias(duracionParqueo);
			if(cantidadDias > 0) {		
				System.out.println("Dias > 0");
				return calcularCobroDiasMayorACeroMoto(duracionParqueo, cantidadDias);
			} else {
				System.out.println("Dias = 0, entonces cobro el dia");
				return valorDiaMoto;		
			}
		}		
	}
	
	public double calcularCobroMenorANueveHorasMoto(Duration duracionParqueo){
		int cantidadHoras = (int)(Math.ceil((float)obtenerMinutos(duracionParqueo)/60));
		return valorHoraMoto*cantidadHoras;
	}
	
	public double calcularCobroDiasMayorACeroMoto(Duration duracionParqueo, long cantidadDias){
		//long cantidadMinutosUltimoDia = duracionParqueo.getStandardMinutes()-cantidadDias*MINUTOS_DIA;
		int cantidadHorasUlitmoDia = Math.abs((int)(Math.ceil((float)(obtenerMinutos(duracionParqueo)-cantidadDias*MINUTOS_DIA)/60)));
		if(cantidadHorasUlitmoDia >= 9){
			cantidadDias = cantidadDias+1;
			cantidadHorasUlitmoDia = 0;
		}
		return valorDiaMoto*cantidadDias + valorHoraMoto*cantidadHorasUlitmoDia;
	}
	
	public long obtenerMinutos(Duration duracionParqueo){
		return duracionParqueo.getStandardMinutes();
	}
	
	public long obtenerDias(Duration duracionParqueo){
		return duracionParqueo.getStandardDays();
	}
	
	public double calcularCobroCilindraje(Vehiculo vehiculoASalir){
		if(vehiculoASalir.getCilindraje() > 500){
			return valorAdicionalMoto;
		}else {
			return 0;
		}
	}	
}

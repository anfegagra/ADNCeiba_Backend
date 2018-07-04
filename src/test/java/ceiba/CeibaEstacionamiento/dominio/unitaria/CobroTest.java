package ceiba.CeibaEstacionamiento.dominio.unitaria;

import static org.junit.Assert.*;

import org.joda.time.Duration;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import ceiba.CeibaEstacionamiento.dominio.Cobro;
import ceiba.CeibaEstacionamiento.dominio.Fecha;
import ceiba.CeibaEstacionamiento.dominio.Vehiculo;
import ceiba.CeibaEstacionamiento.dominio.Vigilante;
import ceiba.CeibaEstacionamiento.testdatabuilder.MotoTestDataBuilder;

public class CobroTest {
	
	@Spy
	Cobro spyCobro;
	
	@Before public void initMocks() {
	       MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testRegistrarSalidaCarroMenorANueveHoras() {
		//Arrange
		double total = 1000;
		Duration duracionParqueo = new Duration(0);
		Mockito.doReturn((long)4).when(spyCobro).obtenerMinutos(Mockito.any());
		Mockito.doReturn((double)1000).when(spyCobro).calcularCobroMenorANueveHorasCarro(Mockito.any());
		
		//Act
		double resultado = spyCobro.registrarSalidaCarro(duracionParqueo);
		
		//Assert
		assertEquals(total, resultado, 0);		
	}
	
	@Test
	public void testRegistrarSalidaCarroMayorAUnDia() {
		//Arrange
		double total = 24000;
		Duration duracionParqueo = new Duration(0);
		Mockito.doReturn((long)12961).when(spyCobro).obtenerMinutos(Mockito.any());
		Mockito.doReturn((long)2).when(spyCobro).obtenerDias(Mockito.any());
		Mockito.doReturn((double)24000).when(spyCobro).calcularCobroDiasMayorACeroCarro(Mockito.any(), Mockito.anyLong());
		
		//Act
		double resultado = spyCobro.registrarSalidaCarro(duracionParqueo);
		
		//Assert
		assertEquals(total, resultado, 0);		
	}
	
	@Test
	public void testRegistrarSalidaCarroDiaIgualACeroYMayorANueveHoras() {
		//Arrange
		double total = 8000;
		Duration duracionParqueo = new Duration(0);
		Mockito.doReturn((long)12961).when(spyCobro).obtenerMinutos(Mockito.any());
		Mockito.doReturn((long)0).when(spyCobro).obtenerDias(Mockito.any());
		//Mockito.doReturn((double)24000).when(spyCobro).calcularCobroDiasMayorACeroCarro(Mockito.any(), Mockito.anyLong());
		//Mockito.doReturn((double)1000).when(spyCobro).calcularCobroMenorANueveHorasCarro(Mockito.any());
		
		//Act
		double resultado = spyCobro.registrarSalidaCarro(duracionParqueo);
		
		//Assert
		assertEquals(total, resultado, 0);		
	}

	@Test
	public void testRegistrarSalidaMotoMenorANueveHoras() {
		//Arrange
		double total = 500;
		Duration duracionParqueo = new Duration(0);
		Mockito.doReturn((long)4).when(spyCobro).obtenerMinutos(Mockito.any());
		Mockito.doReturn((double)500).when(spyCobro).calcularCobroMenorANueveHorasMoto(Mockito.any());
		
		//Act
		double resultado = spyCobro.registrarSalidaMoto(duracionParqueo);
		
		//Assert
		assertEquals(total, resultado, 0);		
	}
	
	@Test
	public void testRegistrarSalidaMotoMayorAUnDia() {
		//Arrange
		double total = 12000;
		Duration duracionParqueo = new Duration(0);
		Mockito.doReturn((long)12961).when(spyCobro).obtenerMinutos(Mockito.any());
		Mockito.doReturn((long)2).when(spyCobro).obtenerDias(Mockito.any());
		//Mockito.doReturn((double)12000).when(spyCobro).calcularCobroDiaMayorACeroMoto(Mockito.any(), Mockito.anyLong());
		Mockito.doReturn((double)12000).when(spyCobro).calcularCobroDiasMayorACeroMoto(Mockito.any(), Mockito.anyLong());
		
		//Act
		double resultado = spyCobro.registrarSalidaMoto(duracionParqueo);
		
		//Assert
		assertEquals(total, resultado, 0);		
	}
	
	@Test
	public void testRegistrarSalidaMotoDiaIgualACeroYMayorANueveHoras() {
		//Arrange
		double total = 4000;
		Duration duracionParqueo = new Duration(0);
		Mockito.doReturn((long)12961).when(spyCobro).obtenerMinutos(Mockito.any());
		Mockito.doReturn((long)0).when(spyCobro).obtenerDias(Mockito.any());
		
		//Act
		double resultado = spyCobro.registrarSalidaMoto(duracionParqueo);
		
		//Assert
		assertEquals(total, resultado, 0);		
	}
	
	@Test
	public void testCalcularCobroCilindrajeMenorAQuinientos(){
		//Arrange
		Cobro cobro = new Cobro();
		double total = 0;
		Vehiculo vehiculo = new MotoTestDataBuilder().build();
		
		//Act
		double resultado = cobro.calcularCobroCilindraje(vehiculo);
		
		//Assert
		assertEquals(total, resultado, 0);
	}
	
	@Test
	public void testCalcularCobroCilindrajeMayorAQuinientos(){
		//Arrange
		Cobro cobro = new Cobro();
		double total = 2000;
		Vehiculo vehiculo = new MotoTestDataBuilder().withCilindraje(600).build();
		
		//Act
		double resultado = cobro.calcularCobroCilindraje(vehiculo);
		
		//Assert
		assertEquals(total, resultado, 0);
	}
	
	@Test
	public void testCalcularCobroMenorANueveHorasMoto(){
		//Arrange
		double total = 500;
		Duration duracionParqueo = new Duration(0);
		Mockito.doReturn((long)1).when(spyCobro).obtenerMinutos(Mockito.any());
		
		//Act
		double resultado = spyCobro.calcularCobroMenorANueveHorasMoto(duracionParqueo);
		
		//Assert
		assertEquals(total, resultado, 0);
	}
	
	@Test
	public void testCalcularCobroMenorANueveHorasCarro(){
		//Arrange
		double total = 1000;
		Duration duracionParqueo = new Duration(0);
		Mockito.doReturn((long)1).when(spyCobro).obtenerMinutos(Mockito.any());
		
		//Act
		double resultado = spyCobro.calcularCobroMenorANueveHorasCarro(duracionParqueo);
		
		//Assert
		assertEquals(total, resultado, 0);
	}
	
	@Test
	public void testCalcularCobroDiasMayorACeroCarro(){
		//Arrange
		double total = 48000;
		Duration duracionParqueo = new Duration(0);
		long cantidadDias = 5;
		Mockito.doReturn((long)12961).when(spyCobro).obtenerMinutos(duracionParqueo);
		
		//Act		
		double resultado = spyCobro.calcularCobroDiasMayorACeroCarro(duracionParqueo, cantidadDias);
		
		//Assert
		assertEquals(total, resultado, 0);
	}
	
	@Test
	public void testCalcularCobroDiasMayorACeroCarroYMenorANueveHoras(){
		//Arrange
		double total = 11000;
		Duration duracionParqueo = new Duration(0);
		long cantidadDias = 1;
		Mockito.doReturn((long)1561).when(spyCobro).obtenerMinutos(duracionParqueo); //cuando hallo horasUltimoDia, equivale a 3 horas
		
		//Act		
		double resultado = spyCobro.calcularCobroDiasMayorACeroCarro(duracionParqueo, cantidadDias);
		
		//Assert
		assertEquals(total, resultado, 0);
	}
	
	@Test
	public void testCalcularCobroDiasMayorACeroCarroYMayorANueveHoras(){
		//Arrange
		double total = 16000;
		Duration duracionParqueo = new Duration(0);
		long cantidadDias = 1;
		Mockito.doReturn((long)1980).when(spyCobro).obtenerMinutos(duracionParqueo); //cuando hallo horasUltimoDia, equivale a 9 horas
		
		//Act		
		double resultado = spyCobro.calcularCobroDiasMayorACeroCarro(duracionParqueo, cantidadDias);
		
		//Assert
		assertEquals(total, resultado, 0);
	}
	
	@Test
	public void testCalcularCobroDiasMayorACeroMotoYMenorANueveHoras(){
		//Arrange
		double total = 5500;
		Duration duracionParqueo = new Duration(0);
		long cantidadDias = 1;
		Mockito.doReturn((long)1620).when(spyCobro).obtenerMinutos(duracionParqueo);
		
		//Act		
		double resultado = spyCobro.calcularCobroDiasMayorACeroMoto(duracionParqueo, cantidadDias);
		
		//Assert
		assertEquals(total, resultado, 0);
	}
	
	@Test
	public void testCalcularCobroDiasMayorACeroMotoYMayorANueveHoras(){
		//Arrange
		double total = 8000;
		Duration duracionParqueo = new Duration(0);
		long cantidadDias = 1;
		Mockito.doReturn((long)1980).when(spyCobro).obtenerMinutos(duracionParqueo); //cuando hallo horasUltimoDia, equivale a 9 horas
		
		//Act		
		double resultado = spyCobro.calcularCobroDiasMayorACeroMoto(duracionParqueo, cantidadDias);
		
		//Assert
		assertEquals(total, resultado, 0);
	}
	
}

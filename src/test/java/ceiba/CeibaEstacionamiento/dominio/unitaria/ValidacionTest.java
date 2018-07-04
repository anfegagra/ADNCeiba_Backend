package ceiba.CeibaEstacionamiento.dominio.unitaria;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import ceiba.CeibaEstacionamiento.dominio.Fecha;
import ceiba.CeibaEstacionamiento.dominio.Validacion;

public class ValidacionTest {

	@Mock 
	Fecha mockFecha;
	
	@Before public void initMocks() {
	       MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testEsPlacaValidaEmpiezaConAYLunes() {
		//Arrange
		Validacion validacion = new Validacion(mockFecha);
		String placa = "ASD123";
		Mockito.doReturn(1).when(mockFecha).obtenerDia();
		
		//Act
		boolean resultado = validacion.esPlacaValida(placa);
		
		//Assert
		assertTrue(resultado);
	}
	
	@Test
	public void testEsPlacaValidaEmpiezaConAYDomingo() {
		//Arrange
		Validacion validacion = new Validacion(mockFecha);
		String placa = "ASD123";
		Mockito.doReturn(7).when(mockFecha).obtenerDia();
		
		//Act
		boolean resultado = validacion.esPlacaValida(placa);
		
		//Assert
		assertTrue(resultado);
	}
	
	@Test
	public void testEsPlacaValidaEmpiezaConAYMiercoles() {
		//Arrange
		Validacion validacion = new Validacion(mockFecha);
		String placa = "ASD123";
		Mockito.doReturn(3).when(mockFecha).obtenerDia();
		
		//Act
		boolean resultado = validacion.esPlacaValida(placa);
		
		//Assert
		assertFalse(resultado);
	}
	
	@Test
	public void testEsPlacaValidaNoEmpiezaConA() {
		//Arrange
		Validacion validacion = new Validacion(mockFecha);
		String placa = "BSD123";
		
		//Act
		boolean resultado = validacion.esPlacaValida(placa);
		
		//Assert
		assertTrue(resultado);
	}

}

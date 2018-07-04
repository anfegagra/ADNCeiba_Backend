package ceiba.CeibaEstacionamiento.dominio.unitaria;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.hibernate.query.criteria.internal.expression.SearchedCaseExpression.WhenClause;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import ceiba.CeibaEstacionamiento.controlador.Crud;
import ceiba.CeibaEstacionamiento.dominio.Carro;
import ceiba.CeibaEstacionamiento.dominio.Cobro;
import ceiba.CeibaEstacionamiento.dominio.Fecha;
import ceiba.CeibaEstacionamiento.dominio.Moto;
import ceiba.CeibaEstacionamiento.dominio.Parqueadero;
import ceiba.CeibaEstacionamiento.dominio.Validacion;
import ceiba.CeibaEstacionamiento.dominio.Vehiculo;
import ceiba.CeibaEstacionamiento.dominio.Vigilante;
import ceiba.CeibaEstacionamiento.testdatabuilder.CarroTestDataBuilder;
import ceiba.CeibaEstacionamiento.testdatabuilder.MotoTestDataBuilder;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class VigilanteTest {
	
	@Mock
	Crud mockCrud;
	
	@Mock
	Fecha mockFecha;

	@Mock
	Cobro mockCobro;
	
	@Spy
	Vigilante spyVigilante;
		
	@Mock
	Validacion mockValidacion;
	
	@Before public void initMocks() {
	       MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testRegistrarIngresoVehiculoTipoCarro() {
		//Arrange
		Vehiculo vehiculo = new CarroTestDataBuilder().build();
		Parqueadero parqueadero = new Parqueadero();
		Mockito.doReturn(vehiculo).when(spyVigilante).hacerValidaciones(Mockito.any(), Mockito.any());
		
		//Act		
		Vehiculo resultadoVehiculo = spyVigilante.registrarIngresoVehiculo(vehiculo, parqueadero);
		
		//Assert
	    assertEquals(vehiculo, resultadoVehiculo);
	}
	
	@Test
	public void testRegistrarIngresoVehiculoTipoMoto() {
		//Arrange
		Vehiculo vehiculo = new MotoTestDataBuilder().build();
		Parqueadero parqueadero = new Parqueadero();
		Mockito.doReturn(vehiculo).when(spyVigilante).hacerValidaciones(Mockito.any(), Mockito.any());
		
		//Act		
		Vehiculo resultadoVehiculo = spyVigilante.registrarIngresoVehiculo(vehiculo, parqueadero);
		
		//Assert
	    assertEquals(vehiculo, resultadoVehiculo);
	}
	
	@Test
	public void testHacerValidacionesConPlacaInvalida() {		
		//Arrange
		Vehiculo esperado = null;
		Vehiculo vehiculo = new MotoTestDataBuilder().withPlaca("ASD12E").build();
		Vigilante vigilante = new Vigilante(mockValidacion);
		Parqueadero parqueadero = new Parqueadero();
		Mockito.doReturn(false).when(mockValidacion).esPlacaValida(Mockito.anyString());
		
		//Act
		Vehiculo resultado = vigilante.hacerValidaciones(vehiculo, parqueadero);
		
		//Assert
		assertEquals(esperado, resultado);
		
	}
	
	@Test
	public void testHacerValidacionesConPlacaValidaCarroYHayEspacio(){
		//Arrange
		Parqueadero parqueadero = new Parqueadero();
		Vehiculo esperado = null;
		Vehiculo vehiculo = new CarroTestDataBuilder().build();
		Vigilante vigilante = new Vigilante(mockValidacion, mockCrud);
		Mockito.doReturn(true).when(mockValidacion).esPlacaValida(Mockito.anyString());
		Mockito.doReturn(true).when(mockCrud).validarCeldasDisponiblesCarro(vehiculo, parqueadero);
		
		//Act
		Vehiculo resultado = vigilante.hacerValidaciones(vehiculo, parqueadero);
		
		//Assert
		assertEquals(esperado, resultado);
	}
	
	@Test
	public void testHacerValidacionesConPlacaValidaCarroPeroNoEspacio() {		
		//Arrange
		Parqueadero parqueadero = new Parqueadero();
		Vehiculo esperado = null;
		Vehiculo vehiculo = new CarroTestDataBuilder().build();
		Vigilante vigilante = new Vigilante(mockValidacion, mockCrud);
		Mockito.doReturn(true).when(mockValidacion).esPlacaValida(Mockito.anyString());
		Mockito.doReturn(false).when(mockCrud).validarCeldasDisponiblesCarro(vehiculo, parqueadero);
		Mockito.doReturn(false).when(mockCrud).validarCeldasDisponiblesMoto(vehiculo, parqueadero);
		
		//Act
		Vehiculo resultado = vigilante.hacerValidaciones(vehiculo, parqueadero);
		
		//Assert
		assertEquals(esperado, resultado);
		
	}
	
	@Test
	public void testHacerValidacionesConPlacaValidaMotoYHayEspacio() {
		//Arrange
		Parqueadero parqueadero = new Parqueadero();
		Vehiculo esperado = null;
		Vehiculo vehiculo = new MotoTestDataBuilder().build();
		Vigilante vigilante = new Vigilante(mockValidacion, mockCrud);
		Mockito.doReturn(true).when(mockValidacion).esPlacaValida(Mockito.anyString());
		Mockito.doReturn(false).when(mockCrud).validarCeldasDisponiblesCarro(vehiculo, parqueadero);
		Mockito.doReturn(true).when(mockCrud).validarCeldasDisponiblesMoto(vehiculo, parqueadero);
		
		//Act
		Vehiculo resultado = vigilante.hacerValidaciones(vehiculo, parqueadero);
		
		//Assert
		assertEquals(esperado, resultado);
		
	}
	
	@Test
	public void testHacerValidacionesConPlacaValidaMotoPeroNoEspacio() {		
		//Arrange
		Parqueadero parqueadero = new Parqueadero();
		Vehiculo esperado = null;
		Vehiculo vehiculo = new MotoTestDataBuilder().build();
		Vigilante vigilante = new Vigilante(mockValidacion, mockCrud);
		Mockito.doReturn(true).when(mockValidacion).esPlacaValida(Mockito.anyString());
		Mockito.doReturn(false).when(mockCrud).validarCeldasDisponiblesCarro(vehiculo, parqueadero);
		Mockito.doReturn(false).when(mockCrud).validarCeldasDisponiblesMoto(vehiculo, parqueadero);
		
		//Act
		Vehiculo resultado = vigilante.hacerValidaciones(vehiculo, parqueadero);
		
		//Assert
		assertEquals(esperado, resultado);		
	}
	
	@Test
	public void testRegistrarSalidaVehiculoNoExistente(){
		//Arrange
		double totalAPagar = 0;
		String placa = "ERT45I";
		Parqueadero parqueadero = new Parqueadero();
		Vigilante vigilante = new Vigilante(mockCrud);
		Mockito.doReturn(null).when(mockCrud).registrarSalida(placa, parqueadero);
		
		//Act
		double resultado = vigilante.registrarSalidaVehiculo(placa, parqueadero);
		
		//Assert
		assertEquals(totalAPagar, resultado, 0);
	}

	@Test
	public void testRegistrarSalidaVehiculoCarro(){
		//Arrange
		double totalAPagar = 5000;
		String placa = "GFU123";
		Vehiculo vehiculo = new CarroTestDataBuilder().build();
		Parqueadero parqueadero = new Parqueadero();
		Vigilante vigilante = new Vigilante(mockCrud, mockFecha, mockCobro);
		Mockito.doReturn(vehiculo).when(mockCrud).registrarSalida(placa, parqueadero);
		Mockito.doReturn(new DateTime(new Date(2018, 6, 27, 6, 00))).when(mockFecha).obtenerFechaEntrada(Mockito.any());
		Mockito.doReturn(new DateTime(new Date(2018, 6, 27, 10, 00))).when(mockFecha).obtenerFechaActual();
		Mockito.doReturn((double)5000).when(mockCobro).registrarSalidaCarro(Mockito.any());
		
		//Act
		double resultado = vigilante.registrarSalidaVehiculo(placa, parqueadero);
		
		//Assert
		assertEquals(totalAPagar, resultado, 0);
	}
	
	@Test
	public void testRegistrarSalidaVehiculoMoto(){
		//Arrange
		double totalAPagar = 500;
		String placa = "KTF12A";
		Vehiculo vehiculo = new MotoTestDataBuilder().build();
		Parqueadero parqueadero = new Parqueadero();
		Vigilante vigilante = new Vigilante(mockCrud, mockFecha, mockCobro);
		Mockito.doReturn(vehiculo).when(mockCrud).registrarSalida(placa, parqueadero);
		Mockito.doReturn(new DateTime(new Date(2018, 6, 27, 6, 00))).when(mockFecha).obtenerFechaEntrada(Mockito.any());
		Mockito.doReturn(new DateTime(new Date(2018, 6, 27, 7, 00))).when(mockFecha).obtenerFechaActual();
		Mockito.doReturn((double)500).when(mockCobro).registrarSalidaMoto(Mockito.any());
		Mockito.doReturn((double)0).when(mockCobro).calcularCobroCilindraje(Mockito.any());
		
		//Act
		double resultado = vigilante.registrarSalidaVehiculo(placa, parqueadero);
		
		//Assert
		assertEquals(totalAPagar, resultado, 0);
	}
}

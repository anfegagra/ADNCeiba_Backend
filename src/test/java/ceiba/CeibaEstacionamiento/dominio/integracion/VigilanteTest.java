package ceiba.CeibaEstacionamiento.dominio.integracion;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import ceiba.CeibaEstacionamiento.controlador.Crud;
import ceiba.CeibaEstacionamiento.dominio.Carro;
import ceiba.CeibaEstacionamiento.dominio.Cobro;
import ceiba.CeibaEstacionamiento.dominio.Fecha;
import ceiba.CeibaEstacionamiento.dominio.Moto;
import ceiba.CeibaEstacionamiento.dominio.Parqueadero;
import ceiba.CeibaEstacionamiento.dominio.Validacion;
import ceiba.CeibaEstacionamiento.dominio.Vehiculo;
import ceiba.CeibaEstacionamiento.dominio.Vigilante;
import ceiba.CeibaEstacionamiento.dominio.repositorio.RepositorioVehiculo;
import ceiba.CeibaEstacionamiento.modelo.ModeloVehiculo;
import ceiba.CeibaEstacionamiento.testdatabuilder.CarroTestDataBuilder;
import ceiba.CeibaEstacionamiento.testdatabuilder.MotoTestDataBuilder;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations="classpath:application-test.properties")
public class VigilanteTest {
	
	@Autowired
	Validacion validacion;
	
	@Autowired
	Fecha fecha;
	
	@Autowired
	Crud crud;
	
	@Autowired
	Cobro cobro;
	
	@Autowired
	RepositorioVehiculo repositorioVehiculo;

	@Before
	public void setUp(){
		//repositorioVehiculo.deleteAll();
		//Para el metodo testRegistrarSalidaVehiculoCarro
		/*ModeloVehiculo modeloVehiculo = new ModeloVehiculo("JUN258", "C", 200, "Activo", new Date((2018-1900), 5, 26, 13, 00));
		repositorioVehiculo.save(modeloVehiculo);*/
	}
	
	/*@After
	public void tearDown(){
		repositorioVehiculo.deleteAll();
	}*/
		
	@Test
	public void testRegistrarIngresoVehiculoTipoCarro() {
		//Arrange
		Vigilante vigilante = new Vigilante(validacion, crud);
		Vehiculo vehiculo = new CarroTestDataBuilder().withCilindraje(2000).build();
		//System.out.println("1: " + vehiculo.getPlaca());
		Parqueadero parqueadero = new Parqueadero();
		
		//Act		
		Vehiculo resultadoVehiculo = vigilante.registrarIngresoVehiculo(vehiculo, parqueadero);
		//System.out.println("2: " + resultadoVehiculo.getPlaca());
		
		//Assert
	    assertEquals(vehiculo.getPlaca(), resultadoVehiculo.getPlaca());
	}
	
	@Test
	public void testRegistrarIngresoVehiculoTipoCarroYParqueaderoLLeno() {
		//Arrange
		for(int i=0; i<21; i++){
			String placa = "TGB12" + i;
			ModeloVehiculo modeloVehiculo = new ModeloVehiculo(placa, "C", 1250, "Activo");
			repositorioVehiculo.save(modeloVehiculo);
		}
		
		Vigilante vigilante = new Vigilante(validacion, crud);
		Vehiculo vehiculo = new CarroTestDataBuilder().withCilindraje(2000).build();
		Parqueadero parqueadero = new Parqueadero();
		
		//Act		
		Vehiculo resultadoVehiculo = vigilante.registrarIngresoVehiculo(vehiculo, parqueadero);
		
		//Assert
	    assertNull(resultadoVehiculo);
	}
	
	@Test
	public void testRegistrarIngresoVehiculoTipoMoto() {
		//Arrange
		Vigilante vigilante = new Vigilante(validacion, crud);
		Vehiculo vehiculo = new MotoTestDataBuilder().withCilindraje(600).build();
		Parqueadero parqueadero = new Parqueadero();		
		
		//Act		
		Vehiculo resultadoVehiculo = vigilante.registrarIngresoVehiculo(vehiculo, parqueadero);
		
		//Assert
	    assertEquals(vehiculo.getPlaca(), resultadoVehiculo.getPlaca());
	}
	
	// Retorna null si es un dia diferente a Lunes o Domingo
	/*@Test
	public void testRegistrarIngresoVehiculoTipoCarroPlacaInvalida() {
		//Arrange
		Vigilante vigilante = new Vigilante(validacion, crud);
		Vehiculo vehiculo = new CarroTestDataBuilder().withPlaca("AER147").build();
		Parqueadero parqueadero = new Parqueadero();		
		
		//Act		
		Vehiculo resultadoVehiculo = vigilante.registrarIngresoVehiculo(vehiculo, parqueadero);
		
		//Assert
	    assertNull(resultadoVehiculo);
	}*/
	
	/*@Test
	public void testRegistrarIngresoVehiculoTipoMotoPlacaInvalida() {
		//Arrange
		Vigilante vigilante = new Vigilante(validacion, crud);
		Vehiculo vehiculo = new MotoTestDataBuilder().withPlaca("AFH42J").build();
		Parqueadero parqueadero = new Parqueadero();		
		
		//Act		
		Vehiculo resultadoVehiculo = vigilante.registrarIngresoVehiculo(vehiculo, parqueadero);
		
		//Assert
	    assertNull(resultadoVehiculo);
	}*/
	
	// Retorna null porque el vehiculo a ingresar ya se encuentra en el parqueadero
	@Test
	public void testRegistrarIngresoVehiculoTipoCarroPlacaValidaPeroYaEsta() {
		//Arrange
		ModeloVehiculo modeloVehiculo = new ModeloVehiculo("EJK426", "C", 1250, "Activo");
		repositorioVehiculo.save(modeloVehiculo);
		
		Vigilante vigilante = new Vigilante(validacion, crud);
		Vehiculo vehiculo = new CarroTestDataBuilder().withPlaca("EJK426").build();
		Parqueadero parqueadero = new Parqueadero();		
		
		//Act		
		Vehiculo resultadoVehiculo = vigilante.registrarIngresoVehiculo(vehiculo, parqueadero);
		
		//Assert
	    assertNull(resultadoVehiculo);
	    repositorioVehiculo.deleteAll();
	}
	
	@Test
	public void testRegistrarSalidaVehiculoNoExistente(){
		//Arrange
		double total = 0;
		String placa = "ERT45I";
		Parqueadero parqueadero = new Parqueadero();
		Vigilante vigilante = new Vigilante(crud, fecha, cobro);		
		
		//Act
		double resultado = vigilante.registrarSalidaVehiculo(placa, parqueadero);
		
		//Assert
		assertEquals(total, resultado, 0);		
	}
	
	@Test
	public void testRegistrarSalidaVehiculoCarroMenosDeUnMinuto(){
		//Arrange		
		ModeloVehiculo modeloVehiculo = new ModeloVehiculo("MKI789", "C", 200, "Activo");
		repositorioVehiculo.save(modeloVehiculo);
		double total = 0;
		String placa = "MKI789";
		Parqueadero parqueadero = new Parqueadero();
		Vigilante vigilante = new Vigilante(crud, fecha, cobro);		
		
		//Act
		double resultado = vigilante.registrarSalidaVehiculo(placa, parqueadero);
		
		//Assert
		assertEquals(total, resultado, 0);		
	}
	
	@Test
	public void testRegistrarSalidaVehiculoMotoMenosDeUnMinuto(){
		//Arrange		
		ModeloVehiculo modeloVehiculo = new ModeloVehiculo("TCX197", "M", 200, "Activo");
		repositorioVehiculo.save(modeloVehiculo);
		double total = 0;
		String placa = "TCX197";
		Parqueadero parqueadero = new Parqueadero();
		Vigilante vigilante = new Vigilante(crud, fecha, cobro);		
		
		//Act
		double resultado = vigilante.registrarSalidaVehiculo(placa, parqueadero);
		
		//Assert
		assertEquals(total, resultado, 0);		
	}
	
	// Un dia y alunas horas
	/*@Test
	public void testRegistrarSalidaVehiculoCarro(){
		//Arrange
		
		double total = 11000;
		String placa = "JUN258";
		Parqueadero parqueadero = new Parqueadero();
		Vigilante vigilante = new Vigilante(crud, fecha, cobro);		
		
		//Act
		double resultado = vigilante.registrarSalidaVehiculo(placa, parqueadero);
		
		//Assert
		//assertEquals(total, resultado, 0);
		assertEquals(total, resultado, 0);
		
	}*/

}

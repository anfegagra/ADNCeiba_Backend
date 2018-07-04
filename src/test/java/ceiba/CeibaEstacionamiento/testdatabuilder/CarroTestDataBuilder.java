package ceiba.CeibaEstacionamiento.testdatabuilder;

import java.util.Date;

import ceiba.CeibaEstacionamiento.dominio.Carro;

public class CarroTestDataBuilder {
	private String placa;	
	private String tipo;
	private int cilindraje;
	
	public CarroTestDataBuilder(){
		this.placa = "GFU123";
		this.tipo = "C";
		this.cilindraje = 1250;
	}
	
	public CarroTestDataBuilder withPlaca(String placa){
		this.placa = placa;
		return this;
	}
	
	public CarroTestDataBuilder withCilindraje(int cilindraje){
		this.cilindraje = cilindraje;
		return this;
	}
	
	public Carro build(){
		return new Carro(this.placa, this.tipo, this.cilindraje);
	}
}

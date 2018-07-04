package ceiba.CeibaEstacionamiento.testdatabuilder;

import ceiba.CeibaEstacionamiento.dominio.Moto;

public class MotoTestDataBuilder {
	private String placa;	
	private String tipo;	
	private int cilindraje;
	
	public MotoTestDataBuilder(){
		this.placa = "KTF12A";
		this.tipo = "M";
		this.cilindraje = 200;
	}
	
	public MotoTestDataBuilder withPlaca(String placa){
		this.placa = placa;
		return this;
	}
		
	public MotoTestDataBuilder withCilindraje(int cilindraje){
		this.cilindraje = cilindraje;
		return this;
	}
	
	public Moto build(){
		return new Moto(this.placa, this.tipo, this.cilindraje);
	}
}

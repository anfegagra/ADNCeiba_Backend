package ceiba.CeibaEstacionamiento.dominio;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.stereotype.Service;

public class Vehiculo{
	
	private String placa;
	
	private String tipo;	
	
	private int cilindraje;
	//private String estado;
	//private Date horaIngreso;
	//private Date horaSalida;
	private String estado = "Activo";
	
	private Date fechaIngreso;
	private String fechaAMostrar;
	
	public Vehiculo(){
		
	}	
	
	public Vehiculo(String placa, String tipo, int cilindraje, String estado, Date fechaIngreso) {
		super();
		this.placa = placa;
		this.tipo = tipo;
		this.cilindraje = cilindraje;
		this.estado = estado;
		this.fechaIngreso = fechaIngreso;
	}
	
	public Vehiculo(String placa, String tipo, int cilindraje, Date fechaIngreso) {
		super();
		this.placa = placa;
		this.tipo = tipo;
		this.cilindraje = cilindraje;
		this.fechaIngreso = fechaIngreso;
	}
	
	public Vehiculo(String placa, String tipo, int cilindraje, String estado, Date fechaIngreso, String fecha) {
		super();
		this.placa = placa;
		this.tipo = tipo;
		this.cilindraje = cilindraje;
		this.estado = estado;
		this.fechaIngreso = fechaIngreso;
		this.fechaAMostrar = fecha;
	}

	public Vehiculo(String placa, String tipo, int cilindraje) {
		this.placa = placa;
		this.tipo = tipo;
		this.cilindraje = cilindraje;
	}
	
	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public int getCilindraje() {
		return cilindraje;
	}

	public void setCilindraje(int cilindraje) {
		this.cilindraje = cilindraje;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Date getFechaIngreso() {
		return fechaIngreso;
	}

	public void setFechaIngreso(Date fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}

	public String getFechaAMostrar() {
		return fechaAMostrar;
	}

	public void setFechaAMostrar(String fechaAMostrar) {
		this.fechaAMostrar = fechaAMostrar;
	}
			
}

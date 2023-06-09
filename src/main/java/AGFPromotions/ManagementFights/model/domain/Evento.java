package AGFPromotions.ManagementFights.model.domain;


import java.util.Date;

import AGFPromotions.ManagementFights.model.enums.Modalidad;

public class Evento {
	private int id_evento;
	private String nombre;
	private String recinto;
	private String ciudad;
	private String pais;
	private Modalidad modalidad;
	private Date fecha;
	private Peleador f1;
	private Peleador f2;
	private Matchmaker m1;
	

	public Evento() {
		this.id_evento=0;
		this.nombre="";
		this.recinto="";
		this.ciudad="";
		this.pais="";
		this.modalidad= null;
		this.fecha=null;
		this.f1=null;
		this.f2=null;
		this.m1=null;
	}


	public Evento(int id_evento, String nombre, String recinto, String ciudad, String pais, Date fecha,
			Modalidad modalidad, Peleador f1, Peleador f2, Matchmaker m1) {
		super();
		this.id_evento = id_evento;
		this.nombre = nombre;
		this.recinto = recinto;
		this.ciudad = ciudad;
		this.pais = pais;
		this.modalidad = modalidad;
		this.fecha = fecha;
		this.f1 = f1;
		this.f2 = f2;
		this.m1 = m1;
	}




	public int getId_evento() {
		return id_evento;
	}


	public void setId_evento(int id_evento) {
		this.id_evento = id_evento;
	}


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public String getRecinto() {
		return recinto;
	}


	public void setRecinto(String recinto) {
		this.recinto = recinto;
	}


	public String getCiudad() {
		return ciudad;
	}


	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}


	public String getPais() {
		return pais;
	}


	public void setPais(String pais) {
		this.pais = pais;
	}


	public Modalidad getModalidad() {
		return modalidad;
	}


	public void setModalidad(Modalidad modalidad) {
		this.modalidad = modalidad;
	}


	public Date getFecha() {
		return fecha;
	}


	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}


	public Peleador getF1() {
		return f1;
	}


	public void setF1(Peleador f1) {
		this.f1 = f1;
	}


	public Peleador getF2() {
		return f2;
	}


	public void setF2(Peleador f2) {
		this.f2 = f2;
	}


	public Matchmaker getM1() {
		return m1;
	}


	public void setM1(Matchmaker m1) {
		this.m1 = m1;
	}

	@Override
	public String toString() {
		return "Evento [id_evento=" + id_evento + ", nombre=" + nombre + ", recinto=" + recinto + ", ciudad=" + ciudad
				+ ", pais=" + pais + ", modalidad=" + modalidad + ", fecha=" + fecha + ", f1=" + f1 + ", f2=" + f2
				+ ", m1=" + m1 + "]";
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id_evento;
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Evento other = (Evento) obj;
		if (id_evento != other.id_evento)
			return false;
		return true;
	}
	
	
	
}

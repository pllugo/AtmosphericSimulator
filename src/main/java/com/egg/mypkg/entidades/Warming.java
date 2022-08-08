package com.egg.mypkg.entidades;



import com.opencsv.bean.CsvBindByName;


public class Warming {
	
	@CsvBindByName
	private String id;
	
	@CsvBindByName
	private Double absorbancia;
	
	@CsvBindByName
	private Double longitudOnda;
	
	public Warming() {
		// TODO Auto-generated constructor stub
	}

	
	public Warming(String id, Double absorbancia, Double longitudOnda) {
		this.id = id;
		this.absorbancia = absorbancia;
		this.longitudOnda = longitudOnda;
	}



	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Double getAbsorbancia() {
		return absorbancia;
	}

	public void setAbsorbancia(Double absorbancia) {
		this.absorbancia = absorbancia;
	}

	public Double getLongitudOnda() {
		return longitudOnda;
	}

	public void setLongitudOnda(Double longitudOnda) {
		this.longitudOnda = longitudOnda;
	}
	
	
}

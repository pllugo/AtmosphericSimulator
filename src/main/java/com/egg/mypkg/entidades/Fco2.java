package com.egg.mypkg.entidades;

import com.opencsv.bean.CsvBindByName;

public class Fco2 {

	@CsvBindByName
	private String id;
	
	@CsvBindByName
	private Double f_co2;
	
	public Fco2() {
		// TODO Auto-generated constructor stub
	}

	public Fco2(String id, Double f_co2) {
		this.id = id;
		this.f_co2 = f_co2;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Double getF_co2() {
		return f_co2;
	}

	public void setF_co2(Double f_co2) {
		this.f_co2 = f_co2;
	}
	
	
	
}

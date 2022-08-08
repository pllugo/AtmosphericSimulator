package com.egg.mypkg.entidades;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Entity
@Getter @Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class Calentamiento {
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name="uuid", strategy = "uuid2")
	private String id;
	
	
	private String nombreCompuesto;
	private String formulaCompuesto;
	private Double pesoMolecular;
	private Double concentracion;
	private Double pasoOptico;
	private Double constanteOh;
	private Double constanteCl;
	private Double constanteO3;
	private Double constanteNo3;
	private Double gwp20;
	private Double gwp100;
	private Double gwp500;

}

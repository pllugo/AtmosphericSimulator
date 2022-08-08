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
public class Acidificacion {
	
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name="uuid", strategy = "uuid2")
	private String id;
	
	private String nombreCompuesto;
	private String formulaCompuesto;
	private Double pesoMolecular;
	private Integer cloro;
	private Integer fluor;
	private Integer nitrogeno;
	private Integer azufre;
	
	
	private Double potencialAp;
}

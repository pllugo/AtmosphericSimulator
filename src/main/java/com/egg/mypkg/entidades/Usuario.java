package com.egg.mypkg.entidades;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

import com.egg.mypkg.enumeraciones.Rol;

@Entity
public class Usuario {
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name="uuid", strategy = "uuid2")
	private String id;
	
	private String nombre;
	private String email;
	private String password;
	
	@Enumerated(EnumType.STRING)
	private Rol rol;
	
	
	
	public Usuario() {
		// TODO Auto-generated constructor stub
	}

	public Usuario(String id, String nombre, String email, String password, Rol rol) {
		this.id = id;
		this.nombre = nombre;
		this.email = email;
		this.password = password;
		this.rol = rol;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Rol getRol() {
		return rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}
	
	
	
}

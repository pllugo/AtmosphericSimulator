package com.egg.mypkg.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.egg.mypkg.servicios.UsuarioServicio;

@Controller
@RequestMapping("/admin")
public class AdminControlador {
	
	
	@Autowired
	private UsuarioServicio usuarioServicio;
	
	
	@GetMapping("/panel")
	public String panelAdministrativo() {
		return "index.html";
	}
}

package com.egg.mypkg.controladores;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.egg.mypkg.entidades.Usuario;
import com.egg.mypkg.errores.ErrorServicio;
import com.egg.mypkg.servicios.UsuarioServicio;

@Controller
@RequestMapping("/")
public class PortalControlador {

	@Autowired
	private UsuarioServicio usuarioServicio;
	
	@GetMapping("/")
	public String index() {
		return "welcome";
	}
	
	@GetMapping("/registrar")
	public String registrar() {
		return "checkout";
	}
	
	@PostMapping("/registro")
	public String registro(ModelMap modelo,@RequestParam String nombre,@RequestParam String email,@RequestParam String password,@RequestParam String password2) {
		try {
			usuarioServicio.registrarUsuario(nombre, email, password, password2);
			modelo.put("exito", "El Usuario se ha registrado exitosamente");
			return "login";
		} catch (ErrorServicio ex) {
			// TODO Auto-generated catch block
			modelo.put("error", ex.getMessage());
			modelo.put("nombre", nombre);
			modelo.put("email", email);
			return "checkout";
		}
	}
	
	@GetMapping("/login")
	public String login(@RequestParam(required = false) String error, ModelMap modelo) {
		if (error != null) {
			modelo.put("error", "Usuario o contraseña invalidos");
		}
		return "login";
	}
	
	@PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
	@GetMapping("/inicio")
	public String inicio(HttpSession session) {
		Usuario logueado = (Usuario) session.getAttribute("usuariosession");
		if (logueado.getRol().toString().equals("ADMIN")) {
			return "redirect:/admin/panel";
		}
		return "inicio.html";
	}
}

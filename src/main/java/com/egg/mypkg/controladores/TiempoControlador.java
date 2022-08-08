package com.egg.mypkg.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.egg.mypkg.entidades.Tiempo;
import com.egg.mypkg.errores.ErrorServicio;
import com.egg.mypkg.servicios.TiempoServicio;

@Controller
@RequestMapping("/tiempo")
public class TiempoControlador {

	@Autowired
	private TiempoServicio tiempoServicio;
	
	@GetMapping("/registrar")
	public String registrar() {
		return "form-tr";
	}
	
	@PostMapping("/registro")
	public String registro(ModelMap modelo, @RequestParam(required = false) String nombreCompuesto,@RequestParam(required = false) String formulaCompuesto,@RequestParam(required = false) String constanteOh,@RequestParam(required = false) String constanteCl,@RequestParam(required = false) String constanteO3,@RequestParam(required = false) String constanteNo3) {
		try {
			tiempoServicio.crearTiempo(nombreCompuesto, formulaCompuesto, constanteOh, constanteCl, constanteO3, constanteNo3);
			modelo.put("exito", "Se pudo calcular los tiempos de vida atmosfericos exitosamente");
			return "redirect:../tiempo/lista";
		} catch (ErrorServicio ex) {
			// TODO Auto-generated catch block
			
			modelo.put("exito", ex.getMessage());
			return "form-tr";
		}
	}
	
	@GetMapping("/lista")
	public String lista(ModelMap modelo) {
		List<Tiempo> tiempos = tiempoServicio.listarTodosLosTiempos();
		modelo.addAttribute("tiempos", tiempos);
		return "tr-list";
	}
	
	@GetMapping("/eliminar/{id}")
	public String eliminar(@PathVariable String id) {
		
		try {
			tiempoServicio.eliminarTiempo(id);
			return "redirect:../lista";
        } catch (Exception e) {
            return "tr-list";
        }
		
		
	}
}

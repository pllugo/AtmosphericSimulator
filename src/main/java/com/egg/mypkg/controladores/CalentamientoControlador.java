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
import org.springframework.web.multipart.MultipartFile;

import com.egg.mypkg.entidades.Calentamiento;

import com.egg.mypkg.errores.ErrorServicio;
import com.egg.mypkg.servicios.CalentamientoServicio;

@Controller
@RequestMapping("/calentamiento")
public class CalentamientoControlador {

	@Autowired
	private CalentamientoServicio calentamientoServicio;

	@GetMapping("/registrar")
	public String registrar(ModelMap modelo) {
		
		return "form-gwp";
	}
	
	
	@PostMapping("/registro")
	public String registro(@RequestParam(required = true) String concentracion,@RequestParam(required = true) String pasoOptico,@RequestParam(required = true) String constanteOh,@RequestParam(required = true) String constanteCl,@RequestParam(required = true) String constanteO3,@RequestParam(required = true) String constanteNo3,@RequestParam(required = true) String pesoMolecular,@RequestParam(required = true) String nombreCompuesto,@RequestParam(required = true) String formulaCompuesto,@RequestParam(required = true) MultipartFile file, ModelMap modelo) {
		try {
			calentamientoServicio.guardarCalentamiento(concentracion, pasoOptico, constanteOh, constanteCl, constanteO3, constanteNo3, pesoMolecular, nombreCompuesto, formulaCompuesto, file);
			modelo.put("exito", "Se pudo calcular el GWP correctamente");
			return "redirect:../calentamiento/lista";
		} catch (ErrorServicio e) {
			// TODO Auto-generated catch block
			modelo.put("error", "No se pudo calcular el GWP");
			return "form-gwp";
		}
	}
	
	@GetMapping("/lista")
	public String listar(ModelMap modelo) {
		List<Calentamiento> listaCalentamiento = calentamientoServicio.listarTodos();
		modelo.addAttribute("listaCalentamiento", listaCalentamiento);
		return "gwp-list";
	}
	
	
	@GetMapping("/eliminar/{id}")
	public String eliminar(@PathVariable String id) {
		try {
			calentamientoServicio.eliminarCalentamiento(id);
			return "redirect:../lista";
		} catch (Exception e) {
			// TODO: handle exception
			return "redirect:../inicio";
		}
		
		
	}
}

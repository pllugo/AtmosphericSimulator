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

import com.egg.mypkg.entidades.Acidificacion;

import com.egg.mypkg.errores.ErrorServicio;
import com.egg.mypkg.servicios.AcidificacionServicio;

@Controller
@RequestMapping("/acidification")
public class AcidificacionControlador {

	
	@Autowired
	private AcidificacionServicio acidificacionServicio;
	
	@GetMapping("/registrar")
	public String registrar() {
		return "form-ap.html";
	}
	
	@PostMapping("/registro")
	public String registro(ModelMap modelo,@RequestParam(required = false) String nombreCompuesto,@RequestParam(required = false) String formulaCompuesto,@RequestParam(required = false) String pesoMolecular,@RequestParam(required = false) Integer cloro,@RequestParam(required = false) Integer fluor,@RequestParam(required = false) Integer nitrogeno,@RequestParam(required = false) Integer azufre) {
		try {
			acidificacionServicio.crearPotencialAp(nombreCompuesto, formulaCompuesto, pesoMolecular, cloro, fluor, nitrogeno, azufre);
			modelo.put("exito", "Se pudo calcular el AP correctamente");
			return "redirect:../acidification/lista";
		} catch (ErrorServicio e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			modelo.put("error", "No se pudo registrar");
			return "form-ap.html";
		}
	}
	
	@GetMapping("/modificarap/{id}")
	public String modificarAp(@PathVariable String id, ModelMap modelo) {
		Acidificacion acidificacion = acidificacionServicio.buscarPorId(id);
		modelo.put("ap", acidificacion);
		return "ap-modify";
		
		
	}
	
	@PostMapping("/modificarap/{id}")
	public String modificarAp(ModelMap modelo,@PathVariable String id, @RequestParam(required = false) String nombreCompuesto,@RequestParam(required = false) String formulaCompuesto,@RequestParam(required = false) String pesoMolecular,@RequestParam(required = false) Integer cloro,@RequestParam(required = false) Integer fluor,@RequestParam(required = false) Integer nitrogeno,@RequestParam(required = false) Integer azufre) {
		try {
			acidificacionServicio.modificarPotencialAp(id, nombreCompuesto, formulaCompuesto, pesoMolecular, cloro, fluor, nitrogeno, azufre);
			return "redirect:../lista";
		} catch (ErrorServicio ex) {
			// TODO Auto-generated catch block
			modelo.put("error", "No se pudo editar");
			return "/inicio";
		}
		
		
	}
	
	
	@GetMapping("/lista")
	public String lista(ModelMap modelo) {
		List<Acidificacion> listarTodosAcidificacion = acidificacionServicio.listarTodosLosPotencialesAp();
		modelo.addAttribute("potenciales", listarTodosAcidificacion);
		return "ap-list";
	}
	
	@GetMapping("/eliminar/{id}")
	public String eliminar(@PathVariable String id) {
		
		try {
			acidificacionServicio.eliminarAp(id);
			return "redirect:../lista";
        } catch (Exception e) {
            return "redirect:..index";
        }
		
		
	}
}

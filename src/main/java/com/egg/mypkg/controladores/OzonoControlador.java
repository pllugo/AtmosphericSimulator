package com.egg.mypkg.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.egg.mypkg.entidades.Ozono;
import com.egg.mypkg.errores.ErrorServicio;
import com.egg.mypkg.servicios.OzonoServicio;

@Controller
@RequestMapping("/pocp")
public class OzonoControlador {

	
	@Autowired
	private OzonoServicio ozonoServicio;
	
	@GetMapping("/registrar")
	public String registrar() {
		return "form-pocp";
	}
	
	@PostMapping("/registro")
	public String registro(ModelMap modelo, @RequestParam(required = false) String nombreCompuesto, @RequestParam(required = false) String formulaCompuesto,@RequestParam(required = false) String pesoMolecular,@RequestParam(required = false) String constantekoh,@RequestParam(required = false) String constanteko3,@RequestParam(required = false) String rendimientoCompuestoConOh,@RequestParam(required = false) String clase_voc,@RequestParam(required = false) String region,@RequestParam(required = false) Integer numeroEnlaces,@RequestParam(required = false) Integer numeroCarbonos,@RequestParam(required = false) String constantekOhProductoNoReactivo,@RequestParam(required = false) String rendimientoProductoNoReactivo,@RequestParam(required = false) Integer numeroEnlacesProducto,@RequestParam(required = false) String claseCov_p,@RequestParam(required = false) String covsConOzono,@RequestParam(required = false) String covAromatico) {
		try {
			ozonoServicio.crearPotencialOzono(nombreCompuesto, formulaCompuesto, pesoMolecular, constantekoh, constanteko3, rendimientoCompuestoConOh, clase_voc, region, numeroEnlaces, numeroCarbonos, constantekOhProductoNoReactivo, rendimientoProductoNoReactivo, numeroEnlacesProducto, claseCov_p, covsConOzono, covAromatico);
			modelo.put("exito", "Se ha calculado el POCP exitosamente");
			return "redirect:../pocp/lista";
		} catch (ErrorServicio e) {
			// TODO Auto-generated catch block
			modelo.put("error", "Se ha generado un error, por favor, intente nuevamente");
			return "form-pocp";
		}
	}
	
	@GetMapping("/lista")
	public String lista(ModelMap modelo) {
		List<Ozono> listarTodos = ozonoServicio.listarOzono();
		modelo.addAttribute("potencialesPocp", listarTodos);
		return "pocp-list";
	}
}

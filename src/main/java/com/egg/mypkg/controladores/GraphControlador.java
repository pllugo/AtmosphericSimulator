package com.egg.mypkg.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.egg.mypkg.entidades.Ozono;
import com.egg.mypkg.servicios.AcidificacionServicio;
import com.egg.mypkg.servicios.OzonoServicio;

@Controller
@RequestMapping("/data")
public class GraphControlador {

	@Autowired
	private AcidificacionServicio acidificacionServicio;
	
	@Autowired
	private OzonoServicio ozonoServicio;
	
	@GetMapping("/display")
	public String barGraph(ModelMap modelo) {
		List<Double> lista = acidificacionServicio.potenciales();
		List<String> listaCompAp = acidificacionServicio.listaDeNombres();
		List<Double> listaPocp = ozonoServicio.listarPotencialesPocp();
		List<String> listaCompPocp = ozonoServicio.listarNombresDeCompuestos();
		modelo.addAttribute("lista", lista);
		modelo.addAttribute("listaCompAp", listaCompAp);
		modelo.addAttribute("listaPocp", listaPocp);
		modelo.addAttribute("listaCompPocp", listaCompPocp);
		return "dashboard";
	}
}

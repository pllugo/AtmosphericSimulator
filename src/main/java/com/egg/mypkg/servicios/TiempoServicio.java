package com.egg.mypkg.servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.egg.mypkg.entidades.Tiempo;
import com.egg.mypkg.errores.ErrorServicio;
import com.egg.mypkg.repositorios.TiempoRepositorio;

@Service
public class TiempoServicio {

	
	@Autowired
	private TiempoRepositorio tiempoRepositorio;
	
	@Transactional
	public void crearTiempo(String nombreCompuesto, String formulaCompuesto, String constanteOh, String constanteCl, String constanteO3, String constanteNo3) throws ErrorServicio {
		validar(nombreCompuesto, formulaCompuesto, constanteOh, constanteCl, constanteO3, constanteNo3);
		Double constantekoh = Double.parseDouble(constanteOh);
		Double constantekcl = Double.parseDouble(constanteCl);
		Double constanteko3 = Double.parseDouble(constanteO3);
		Double constantekno3 = Double.parseDouble(constanteNo3);
		Tiempo tiempo = new Tiempo();
		tiempo.setNombreCompuesto(nombreCompuesto);
		tiempo.setFormulaCompuesto(formulaCompuesto);
		tiempo.setConstanteOh(constantekoh);
		tiempo.setConstanteCl(constantekcl);
		tiempo.setConstanteO3(constanteko3);
		tiempo.setConstanteNo3(constantekno3);
		tiempo.setTiempoOh(verificarConstantes("oh", constantekoh));
		tiempo.setTiempoCl(verificarConstantes("cl", constantekcl));
		tiempo.setTiempoO3(verificarConstantes("o3", constanteko3));
		tiempo.setTiempoNo3(verificarConstantes("no3", constantekno3));
		tiempoRepositorio.save(tiempo);
	}
	
	
	@Transactional
	public void modificarTiempo(String id, String nombreCompuesto, String formulaCompuesto, String constanteOh, String constanteCl, String constanteO3, String constanteNo3) throws ErrorServicio {
		validar(nombreCompuesto, formulaCompuesto, constanteOh, constanteCl, constanteO3, constanteNo3);
		Double constantekoh = Double.parseDouble(constanteOh);
		Double constantekcl = Double.parseDouble(constanteCl);
		Double constanteko3 = Double.parseDouble(constanteO3);
		Double constantekno3 = Double.parseDouble(constanteNo3);
		Optional<Tiempo> respuesta = tiempoRepositorio.findById(id);
		
		if (respuesta.isPresent()) {
			Tiempo tiempo = respuesta.get();
			tiempo.setNombreCompuesto(nombreCompuesto);
			tiempo.setFormulaCompuesto(formulaCompuesto);
			tiempo.setConstanteOh(constantekoh);
			tiempo.setConstanteCl(constantekcl);
			tiempo.setConstanteO3(constanteko3);
			tiempo.setConstanteNo3(constantekno3);
			tiempo.setTiempoOh(verificarConstantes("oh", constantekoh));
			tiempo.setTiempoCl(verificarConstantes("cl", constantekcl));
			tiempo.setTiempoO3(verificarConstantes("o3", constanteko3));
			tiempo.setTiempoNo3(verificarConstantes("no3", constantekno3));
			tiempoRepositorio.save(tiempo);
		}
	}
	
	public void validar(String nombreCompuesto, String formulaCompuesto, String constanteOh, String constanteCl, String constanteO3, String constanteNo3) throws ErrorServicio {
		if (nombreCompuesto.isEmpty() || nombreCompuesto == null) {
			throw new ErrorServicio("El compuesto no puede ser vacio o nulo");
		}
		if (formulaCompuesto.isEmpty() || formulaCompuesto == null) {
			throw new ErrorServicio("La formula no puede ser vacio o nulo");
		}
		if (constanteOh.isEmpty() || constanteOh == null) {
			throw new ErrorServicio("La constante kOH no puede ser vacio o nulo");
		}
		if (constanteCl.isEmpty() || constanteCl == null) {
			throw new ErrorServicio("La constante kCl no puede ser vacio o nulo");
		}
		if (constanteO3.isEmpty() || constanteO3 == null) {
			throw new ErrorServicio("La constante kO3 no puede ser vacio o nulo");
		}
		if (constanteNo3.isEmpty() || constanteNo3 == null) {
			throw new ErrorServicio("La constante kNO3 no puede ser vacio o nulo");
		}
	}
	
	
	public Double verificarConstantes(String oxidante, Double constante) {
		
		if (constante != 0.0d) {
			return tiempoDeResidencia(oxidante, constante);
		}else {
			return 0.0d;
		}
	}
	
	public Double tiempoDeResidencia(String oxidante, Double constante) {

		Double tiempo = 0.0d;
		if (oxidante.equalsIgnoreCase("oh") || oxidante.equalsIgnoreCase("OH")) {

			tiempo = (1 / (constante * 1000000d)) / 3600.0d;
		}
		if (oxidante.equalsIgnoreCase("cl") || oxidante.equalsIgnoreCase("Cl")) {

			tiempo = (1 / (constante * 10000d)) / 3600.0d;
		}
		if (oxidante.equalsIgnoreCase("o3") || oxidante.equalsIgnoreCase("O3")) {

			tiempo = (1 / (constante* 500000000d)) / 3600.0d;

		}
		if (oxidante.equalsIgnoreCase("no3") || oxidante.equalsIgnoreCase("NO3")) {
			tiempo = (1 / (constante * 700000000000d)) / 3600.0d;

		}
		return tiempo;
		
	}
	
	
	@Transactional(readOnly = true)
	public List<Tiempo> listarTodosLosTiempos(){
		return tiempoRepositorio.findAll();
	}
	
	@Transactional(readOnly = true)
	public Tiempo buscarTiempoPorId(String id) {
		return tiempoRepositorio.getOne(id);
	}
	
	
	@Transactional
	public void eliminarTiempo(String id) {
		tiempoRepositorio.delete(buscarTiempoPorId(id));
	}
}

package com.egg.mypkg.servicios;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.egg.mypkg.entidades.Acidificacion;
import com.egg.mypkg.errores.ErrorServicio;
import com.egg.mypkg.repositorios.AcidificacionRepositorio;

@Service
public class AcidificacionServicio {
	
	@Autowired
	private AcidificacionRepositorio acidificacionRepositorio;
	
	
	@Transactional
	public void crearPotencialAp(String nombreCompuesto, String formulaCompuesto, String pesoMolecular, Integer cloro, Integer fluor, Integer nitrogeno, Integer azufre) throws ErrorServicio {
		
		Double masaMolar = Double.parseDouble(pesoMolecular);
		validar(nombreCompuesto, formulaCompuesto, masaMolar, cloro, fluor, nitrogeno, azufre);
		Acidificacion acidificacion = new Acidificacion();
		acidificacion.setNombreCompuesto(nombreCompuesto);
		acidificacion.setFormulaCompuesto(formulaCompuesto);
		acidificacion.setPesoMolecular(masaMolar);
		acidificacion.setCloro(cloro);
		acidificacion.setFluor(fluor);
		acidificacion.setNitrogeno(nitrogeno);
		acidificacion.setAzufre(azufre);
		acidificacion.setPotencialAp(calcularPotencialAp(masaMolar, cloro, fluor, nitrogeno, azufre));
		acidificacionRepositorio.save(acidificacion);
	}
	
	
	@Transactional
	public void modificarPotencialAp(String idAcidificacion, String nombreCompuesto, String formulaCompuesto, String pesoMolecular, Integer cloro, Integer fluor, Integer nitrogeno, Integer azufre) throws ErrorServicio {
		Optional<Acidificacion> respuestaAp = acidificacionRepositorio.findById(idAcidificacion);
		if (respuestaAp.isPresent()) {
			Acidificacion acidificacion = respuestaAp.get();
			Double masaMolar = Double.parseDouble(pesoMolecular);
			validar(nombreCompuesto, formulaCompuesto, masaMolar, cloro, fluor, nitrogeno, azufre);
			acidificacion.setNombreCompuesto(nombreCompuesto);
			acidificacion.setFormulaCompuesto(formulaCompuesto);
			acidificacion.setPesoMolecular(masaMolar);
			acidificacion.setCloro(cloro);
			acidificacion.setFluor(fluor);
			acidificacion.setNitrogeno(nitrogeno);
			acidificacion.setAzufre(azufre);
			acidificacion.setPotencialAp(calcularPotencialAp(masaMolar, cloro, fluor, nitrogeno, azufre));
			acidificacionRepositorio.save(acidificacion);
		}
	}
	
	
	public void validar(String nombreCompuesto, String formulaCompuesto, Double pesoMolecular, Integer cloro, Integer fluor, Integer nitrogeno, Integer azufre) throws ErrorServicio {
		if (nombreCompuesto.isEmpty() || nombreCompuesto == null) {
			throw new ErrorServicio("El nombre no puede estar nulo o vacio");
		}
		if (formulaCompuesto.isEmpty() || formulaCompuesto == null) {
			throw new ErrorServicio("La fórmula no puede estar nula o vacia");
		}
		if (pesoMolecular == null || pesoMolecular <= 0) {
			throw new ErrorServicio("El peso molecular no puede estar nulo o vacio o negativo");
		}
		if (cloro == null || cloro < 0) {
			throw new ErrorServicio("El cloro no puede estar nulo o negativo");
		}
		if (fluor == null || fluor < 0) {
			throw new ErrorServicio("El fluor no puede estar nulo o negativo");
		}
		if (nitrogeno == null || nitrogeno < 0) {
			throw new ErrorServicio("El nitrogeno no puede estar nulo o negativo");
		}
		if (azufre == null || azufre < 0) {
			throw new ErrorServicio("El azufre no puede estar nulo o negativo");
		}
	}
	
	public Double calcularPotencialAp(Double pesoMolecular, Integer cloro, Integer fluor, Integer nitrogeno, Integer azufre) {
		Double potencial = (64.066d / pesoMolecular)*((cloro + fluor + nitrogeno + 2.0d*azufre)/2.0d);
		return potencial;
	}
	
	
	@Transactional(readOnly = true)
	public List<Acidificacion> listarTodosLosPotencialesAp(){
		return acidificacionRepositorio.findAll();
	}
	
	
	@Transactional(readOnly = true)
	public Acidificacion buscarPorId(String id) {
		return acidificacionRepositorio.getOne(id);
	}
	
	@Transactional
	public void eliminarAp(String id) {
		acidificacionRepositorio.delete(buscarPorId(id));
	}
	
	
	
	@Transactional(readOnly = true)
	public List<Double> potenciales() {
		List<Double> listaAp = new ArrayList();
		List<Acidificacion> listarTodos = listarTodosLosPotencialesAp();
		for (Acidificacion ap : listarTodos) {
			listaAp.add(ap.getPotencialAp());
		}
		return listaAp;
	}
	
	@Transactional(readOnly = true)
	public List<String> listaDeNombres(){
		List<String> listaNombres = new ArrayList();
		List<Acidificacion> listarTodos = listarTodosLosPotencialesAp();
		for (Acidificacion acidificacion : listarTodos) {
			listaNombres.add(acidificacion.getNombreCompuesto());
		}
		return listaNombres;
	}
}

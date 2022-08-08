package com.egg.mypkg.servicios;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.egg.mypkg.entidades.Calentamiento;

import com.egg.mypkg.entidades.Warming;
import com.egg.mypkg.errores.ErrorServicio;
import com.egg.mypkg.repositorios.CalentamientoRepositorio;

@Service
public class CalentamientoServicio {

	/*private String nombreCompuesto;
	private String formulaCompuesto;
	private Double pesoMolecular;
	private Double concentracion;
	private Double pasoOptico;
	private Double constanteOh;
	private Double constanteCl;
	private Double constanteO3;
	private Double constanteNo3;
	private Double gwp20;
	private Double gwp100;
	private Double gwp500;
*/
	
	@Autowired
	private CalentamientoRepositorio calentamientoRepositorio;
	
	@Autowired
	private WarmingServicio warmingServicio;
	
	
	@Transactional
	public void guardarCalentamiento(String concentracion, String pasoOptico, String constanteOh, String constanteCl, String constanteO3, String constanteNo3, String pesoMolecular, String nombreCompuesto, String formulaCompuesto, MultipartFile file) throws ErrorServicio {
		validar(concentracion, pasoOptico, constanteOh, constanteCl, constanteO3, constanteNo3, pesoMolecular);
		Double kOh = Double.parseDouble(constanteOh);
		Double kCl = Double.parseDouble(constanteCl);
		Double kO3 = Double.parseDouble(constanteO3);
		Double kNo3 = Double.parseDouble(constanteNo3);
		Double pas_optico = Double.parseDouble(pasoOptico);
		Double concentracionCompuesto = Double.parseDouble(concentracion);
		Double masaMolar = Double.parseDouble(pesoMolecular);
		Calentamiento globalWarmingPotencial = new Calentamiento();
		globalWarmingPotencial.setConcentracion(concentracionCompuesto);
		globalWarmingPotencial.setConstanteOh(kOh);
		globalWarmingPotencial.setConstanteCl(kCl);
		globalWarmingPotencial.setConstanteO3(kO3);
		globalWarmingPotencial.setConstanteNo3(kNo3);
		globalWarmingPotencial.setPasoOptico(pas_optico);
		
		globalWarmingPotencial.setPesoMolecular(masaMolar);
		Double[] listaFtir;
		List<Warming> archivoFtir = warmingServicio.crearWarming(file);
		
		List<Double> listaSint = calcularListaSint(archivoFtir, concentracionCompuesto, pas_optico);
		Double[] listafSint = calcularfSint(listaSint, leerArchivo());
		
		Double tiempoyears = tiempoYear(kOh, kCl, kO3, kNo3);
		Double ft = calculoFt(tiempoyears);
	
		
		Double rfCorrected = calculoRfCorrected(listafSint, ft);
		
		Double gwp20 = calcularGwp(rfCorrected, masaMolar, tiempoyears, 20.0d, 2.49e-14);
		Double gwp100 = calcularGwp(rfCorrected, masaMolar, tiempoyears, 100.0d, 9.17e-14);
		Double gwp500 = calcularGwp(rfCorrected, masaMolar, tiempoyears, 500.0d, 3.21e-13);
		globalWarmingPotencial.setGwp20(gwp20);
		globalWarmingPotencial.setGwp100(gwp100);
		globalWarmingPotencial.setGwp500(gwp500);
		globalWarmingPotencial.setNombreCompuesto(nombreCompuesto);
		globalWarmingPotencial.setFormulaCompuesto(formulaCompuesto);
		calentamientoRepositorio.save(globalWarmingPotencial);
		
	}
	
	
	
	public void validar(String concentracion, String pasoOptico, String constanteOh, String constanteCl, String constanteO3, String constanteNo3, String pesoMolecular) throws ErrorServicio {
		
		if (concentracion.isEmpty() || concentracion == null) {
			throw new ErrorServicio("La concentración no puede ser negativa o nula");
		}
		if (pasoOptico.isEmpty() || pasoOptico == null) {
			throw new ErrorServicio("El paso óptico no puede ser negativa o nulo");
		}
		if (constanteOh.isEmpty() || constanteOh == null || Double.parseDouble(constanteOh) <= 0) {
			throw new ErrorServicio("La constante kOH no puede ser negativa o nula");
		}
		if (constanteCl.isEmpty() || constanteCl == null || Double.parseDouble(constanteCl) <= 0) {
			throw new ErrorServicio("La constante kCl no puede ser negativa o nula");
		}
		if (constanteO3.isEmpty() || constanteO3 == null || Double.parseDouble(constanteO3) < 0) {
			throw new ErrorServicio("La constante kO3 no puede ser negativa o nula");
		}
		if (constanteNo3.isEmpty() || constanteNo3 == null || Double.parseDouble(constanteNo3) < 0) {
			throw new ErrorServicio("La constante kNO3 no puede ser negativa o nula");
		}
		if (pesoMolecular.isEmpty() || pesoMolecular == null) {
			throw new ErrorServicio("El peso molecular no puede ser negativo o nulo");
		}
		

	}
	
	@Transactional(readOnly = true)
	public List<Double> calcularListaSint(List<Warming> listaArchivo, Double concentracion, Double pasoOptico){
		List<Double> listaSint = new ArrayList();
		
		List<Double> listaAbsorbancia = warmingServicio.listarAbsorbancias(listaArchivo);
		
		for (Double listaDeAbsorbancia : listaAbsorbancia) {
			listaSint.add(listaDeAbsorbancia/(concentracion * pasoOptico) * 2.303d);
		}
		System.out.println(listaAbsorbancia);
		return listaSint;
	}
	
	
	
	public Double[] leerArchivo() throws ErrorServicio {
		Double[] listaCo2 = new Double[2500];
		int i = 0;
		String path = "src\\main\\resources\\referencia_co2.csv";
        String line = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));

            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                listaCo2[i] = Double.parseDouble(values[0]);
                System.out.println(listaCo2[i]);
                i = i + 1;
            }

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

		return listaCo2;
	}
	
	public Double[] convertir(List<Double> lista) {
		Double[] resultado = new Double[lista.size()];
		for (int i = 0; i < resultado.length; i++) {
			resultado[i] = lista.get(i);
			
		}
		return resultado;
	}
	
	public Double[] calcularfSint(List<Double> listaSint, Double[] listaCo2){
		Double[] listafSint = new Double[listaCo2.length];
		Double[] listSint = convertir(listaSint);
		
		
		for (int i = 0; i < listaCo2.length; i++) {
			listafSint[i] = listaCo2[i] * listSint[i] * 1e15 * 2.303;
		}
		return listafSint;
		
	}
	
	public Double tiempoYear(Double constantekOh, Double constantekCl, Double constantekO3, Double constantekNo3) {
		Double tiempokOh = 1/(constantekOh*2000000);
		Double tiempokCl = 1/(constantekCl*10000);
		if (constantekO3 == 0 && constantekNo3 == 0) {
			Double tiempo = (1/(1/tiempokOh + 1/tiempokCl))/(3600*24*365);
			return tiempo;
		}else if(constantekNo3 == 0) {
			
			Double tiempokO3 = 1/(constantekO3*5E8);
			
			Double tiempo = (1/(1/tiempokOh + 1/tiempokCl + 1/tiempokO3))/(3600*24*365);
			return tiempo;
		}else {
			
			Double tiempokNo3 = 1/(constantekNo3*7E11);
			Double tiempo = (1/(1/tiempokOh + 1/tiempokCl + 1/tiempokNo3))/(3600*24*365);
			return tiempo;
		}
	}
	
	
	
	public Double calculoFt(Double tiempoyea) {
		
		return (2.962d*(Math.pow(tiempoyea, 0.9312d))/(1+(2.994d*(Math.pow(tiempoyea, 0.9312d)))));
	}
	
	
	public Double calculoRf(Double[] vectorfSint) {
		return sumaVector(vectorfSint, 0.0d);
	}
	
	public Double calculoRfCorrected(Double[] vectorfSint, Double ft) {
		return sumaVector(vectorfSint, ft);
	}
	
	
	
	public Double sumaVector(Double[] vector, Double ft) {
		Double acum = 0.0d;
		if (ft == 0.0d) {
			for (int i = 0; i < vector.length; i++) {
				acum = acum + vector[i];
			}
			System.out.println("acum sin ft " + acum);
		}else {
			for (int i = 0; i < vector.length; i++) {
				acum = acum + vector[i];
			}
			acum = acum * ft;
			System.out.println("acum con ft " + acum);
		}
		
		return acum;
	}
	
	public Double calcularGwp(Double acumulador, Double pesoMolecular, Double tiempoYears, Double factorTiempo, Double parametroGwp) {
		Double gwp = (acumulador*(28.97/pesoMolecular)*(1e9/5.13e18)*tiempoYears*(1-Math.exp(-factorTiempo/tiempoYears)))/(parametroGwp);
		
		return gwp;
	}
	
	
	
	@Transactional(readOnly = true)
	public List<Calentamiento> listarTodos(){
		return calentamientoRepositorio.findAll();
	}
	
	@Transactional(readOnly = true)
	public Calentamiento buscarPorId(String id) {
		
		Optional<Calentamiento> respuesta = calentamientoRepositorio.findById(id);
		if (respuesta.isPresent()) {
			Calentamiento calentamiento = respuesta.get();
			return calentamiento;
		}else {
			return null;
		}
		
	}
	
	
	@Transactional
	public void eliminarCalentamiento(String id) {
		calentamientoRepositorio.delete(buscarPorId(id));
	}
}

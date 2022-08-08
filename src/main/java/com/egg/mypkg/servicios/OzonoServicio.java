package com.egg.mypkg.servicios;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.egg.mypkg.entidades.Ozono;
import com.egg.mypkg.errores.ErrorServicio;
import com.egg.mypkg.repositorios.OzonoRepositorio;

@Service
public class OzonoServicio {

	
	@Autowired
	private OzonoRepositorio ozonoRepositorio;
	
	
	@Transactional
	public void crearPotencialOzono(String nombreCompuesto, String formulaCompuesto, String pesoMolecular, String constanteOh, String constanteO3, String rendimientoCompuestoConOh, String clase_voc, String region, Integer numeroEnlaces, Integer numeroCarbonos, String constantekOhProductoNoReactivo, String rendimientoProductoNoReactivo, Integer numeroEnlacesProducto, String claseCov_p, String covsConOzono, String covAromatico) throws ErrorServicio {
		
		Double masaMolar = Double.parseDouble(pesoMolecular);
		Double constantekoh = Double.parseDouble(constanteOh);
		Double constanteko3 = Double.parseDouble(constanteO3);
		Double rendimientoconOh = Double.parseDouble(rendimientoCompuestoConOh);
		Double constanteohproductoNoReactivo = Double.parseDouble(constantekOhProductoNoReactivo);
		Double rendimientoproductonoreactivo = Double.parseDouble(rendimientoProductoNoReactivo);
		validar(nombreCompuesto, formulaCompuesto, masaMolar, constantekoh, constanteko3, rendimientoconOh, constanteohproductoNoReactivo, rendimientoproductonoreactivo, clase_voc, region, numeroEnlaces, numeroCarbonos, numeroEnlacesProducto, claseCov_p, covsConOzono, covAromatico);
		Ozono ozono = new Ozono();
		ozono.setNombreCompuesto(nombreCompuesto);
		ozono.setFormulaCompuesto(formulaCompuesto);
		ozono.setPesoMolecular(masaMolar);
		ozono.setConstantekoh(Double.parseDouble(constanteOh));
		ozono.setConstanteko3(Double.parseDouble(constanteO3));
		ozono.setConstantekOhProductoNoReactivo(constanteohproductoNoReactivo);
		ozono.setClase_voc(clase_voc);
		ozono.setClaseCov_p(claseCov_p);
		ozono.setCovAromatico(covAromatico);
		ozono.setCovsConOzono(covsConOzono);
		ozono.setNumeroCarbonos(numeroCarbonos);
		ozono.setNumeroEnlaces(numeroEnlaces);
		ozono.setNumeroEnlacesProducto(numeroEnlacesProducto);
		ozono.setRegion(region);
		ozono.setRendimientoCompuestoConOh(rendimientoconOh);
		ozono.setRendimientoProductoNoReactivo(rendimientoproductonoreactivo);
		ozono.setPotencialPocp(calcularPocp(clase_voc, region, Double.parseDouble(pesoMolecular),
				Double.parseDouble(constanteOh), numeroEnlaces, numeroCarbonos, Double.parseDouble(constantekOhProductoNoReactivo),
				Double.parseDouble(rendimientoProductoNoReactivo), numeroEnlacesProducto, claseCov_p, covsConOzono, covAromatico, Double.parseDouble(rendimientoCompuestoConOh)));
		System.out.println(ozono.getPotencialPocp());
		ozonoRepositorio.save(ozono);
	}
	
	
	
	@Transactional
	public void modificarOzono(String id, String nombreCompuesto, String formulaCompuesto, String pesoMolecular, String constanteOh, String constanteO3, String rendimientoCompuestoConOh, String clase_voc, String region, Integer numeroEnlaces, Integer numeroCarbonos, String constantekOhProductoNoReactivo, String rendimientoProductoNoReactivo, Integer numeroEnlacesProducto, String claseCov_p, String covsConOzono, String covAromatico) throws ErrorServicio {
		Double masaMolar = Double.parseDouble(pesoMolecular);
		Double constantekoh = Double.parseDouble(constanteOh);
		Double constanteko3 = Double.parseDouble(constanteO3);
		Double rendimientoconOh = Double.parseDouble(rendimientoCompuestoConOh);
		Double constanteohproductoNoReactivo = Double.parseDouble(constantekOhProductoNoReactivo);
		Double rendimientoproductonoreactivo = Double.parseDouble(rendimientoProductoNoReactivo);
		Optional<Ozono> respuesta = ozonoRepositorio.findById(id);
		if (respuesta.isPresent()) {
			Ozono ozono = respuesta.get();
			ozono.setNombreCompuesto(nombreCompuesto);
			ozono.setFormulaCompuesto(formulaCompuesto);
			ozono.setPesoMolecular(masaMolar);
			ozono.setConstantekoh(Double.parseDouble(constanteOh));
			ozono.setConstanteko3(Double.parseDouble(constanteO3));
			ozono.setConstantekOhProductoNoReactivo(constanteohproductoNoReactivo);
			ozono.setClase_voc(clase_voc);
			ozono.setClaseCov_p(claseCov_p);
			ozono.setCovAromatico(covAromatico);
			ozono.setCovsConOzono(covsConOzono);
			ozono.setNumeroCarbonos(numeroCarbonos);
			ozono.setNumeroEnlaces(numeroEnlaces);
			ozono.setNumeroEnlacesProducto(numeroEnlacesProducto);
			ozono.setRegion(region);
			ozono.setRendimientoCompuestoConOh(rendimientoconOh);
			ozono.setRendimientoProductoNoReactivo(rendimientoproductonoreactivo);
			ozono.setPotencialPocp(calcularPocp(clase_voc, region, Double.parseDouble(pesoMolecular),
					Double.parseDouble(constanteOh), numeroEnlaces, numeroCarbonos, Double.parseDouble(constantekOhProductoNoReactivo),
					Double.parseDouble(rendimientoProductoNoReactivo), numeroEnlacesProducto, claseCov_p, covsConOzono, covAromatico, Double.parseDouble(rendimientoCompuestoConOh)));
			System.out.println(ozono.getPotencialPocp());
			ozonoRepositorio.save(ozono);
		}
	}
	
	public void validar(String nombreCompuesto, String formulaCompuesto, Double masaMolar, Double constantekoh, Double constanteko3, Double rendimientoconOh, Double constanteohproductoNoReactivo, Double rendimientoproductonoreactivo, String clase_voc, String region, Integer numeroEnlaces, Integer numeroCarbonos,Integer numeroEnlacesProducto, String claseCov_p, String covsConOzono, String covAromatico) throws ErrorServicio {
		if (nombreCompuesto.isEmpty() || nombreCompuesto == null) {
			throw new ErrorServicio("El nombre no puede ser nulo o vacio");
		}
		if (formulaCompuesto.isEmpty() || formulaCompuesto == null) {
			throw new ErrorServicio("La formula no puede ser nulo o vacio");
		}
		if (masaMolar == null || masaMolar <= 0) {
			throw new ErrorServicio("El Peso Molecular no puede ser nulo, vacio o negativo o cero");
		}
		if (constantekoh == null || constantekoh <= 0) {
			throw new ErrorServicio("La constante kOH no puede ser nulo, vacio o negativo o cero");
		}
		if (constanteko3 == null || constanteko3 < 0) {
			throw new ErrorServicio("La constante kO3 no puede ser nulo, vacio o negativo. Por favor, si no posee la kO3 colocar cero");
		}
		if (rendimientoconOh == null || rendimientoconOh < 0.0d) {
			throw new ErrorServicio("El rendimiento con OH no puede ser nulo, vacio o negativo");
		}
		if (constanteohproductoNoReactivo == null || constanteohproductoNoReactivo < 0.0d) {
			throw new ErrorServicio("La constante kOH del producto no reactivo no puede ser nulo, vacio o negativo. Por favor, si no posee la kOH del producto no reactivo, colocar cero");
		}
		if (rendimientoproductonoreactivo == null || rendimientoproductonoreactivo < 0.0d) {
			throw new ErrorServicio("El rendimiento del producto no reactivo con OH no puede ser nulo, vacio o negativo");
		}
		if (clase_voc.isEmpty() || clase_voc == null) {
			throw new ErrorServicio("La clase del VOC no puede ser nulo o vacio");
		}
		if (region.isEmpty() || region == null) {
			throw new ErrorServicio("La región no puede ser nulo o vacio");
		}
		if (numeroEnlaces <= 0) {
			throw new ErrorServicio("El numero de enlaces no puede ser nulo, vacio, negativo o cero");
		}
		if (numeroCarbonos <= 0) {
			throw new ErrorServicio("El numero de carbonos no puede ser nulo, vacio, negativo o cero");
		}
		if (claseCov_p == null || claseCov_p.isEmpty()) {
			throw new ErrorServicio("El numero de carbonos no puede ser nulo, vacio, negativo o cero");
		}
		if (covsConOzono == null || covsConOzono.isEmpty()) {
			throw new ErrorServicio("Debe seleccionar una opción no puede ser nulo, vacio");
		}
		if (numeroEnlacesProducto < 0) {
			throw new ErrorServicio("Debe seleccionar una opción no puede ser nulo, vacio");
		}
	}
	
	
	public Double parametroA(String clase_cov, String region) {
		// TODO Auto-generated method stub

		String[] vectorA = {"aliphatics", "aromatics with 0 alkyl substituents", "aromatics with 1 alkyl substituents",
				"aromatics with 2 alkyl substituents", "aromatics with 3 alkyl substituents" };
		Double[] vectorEuropa = { 100.0, 66.0, 130.0, 173.0, 206.0 };
		Double[] vectorUsa = { 154.0, 25.0, 320.0, 427.0, 509.0 };
		return buscarParametro(clase_cov, region, vectorA, vectorEuropa, vectorUsa);
	}

	public Double parametroB(String region) {
		// TODO Auto-generated method stub
		Double resultado = 0.0d;
		if (region.equalsIgnoreCase("europa")) {
			resultado = 4.0d;
		} else if (region.equalsIgnoreCase("usa")) {
			resultado = 1.0d;
		}
		return resultado;
	}

	public Double parametroAlfa(String region) {
		// TODO Auto-generated method stub
		Double resultado = 0.0d;
		if (region.equalsIgnoreCase("europa")) {
			resultado = 0.56d;
		} else if (region.equalsIgnoreCase("usa")) {
			resultado = 0.37d;
		}
		return resultado;

	}

	public Double parametroC(String region) {
		// TODO Auto-generated method stub
		Double resultado = 0.0d;
		if (region.equalsIgnoreCase("europa")) {
			resultado = 0.0038d;
		} else if (region.equalsIgnoreCase("usa")) {
			resultado = 0.0041d;
		}
		return resultado;
	}

	public Double parametroBeta(String region) {
		// TODO Auto-generated method stub
		Double resultado = 0.0d;
		if (region.equalsIgnoreCase("europa")) {
			resultado = 2.7d;
		} else if (region.equalsIgnoreCase("usa")) {
			resultado = 2.7d;
		}
		return resultado;
	}

	public Double parametroD(String clase_cov, String region) {
		// TODO Auto-generated method stub
		Double resultado = 0.0d;
		if (region.equalsIgnoreCase("europa") && clase_cov.equalsIgnoreCase("aliphatics")) {
			resultado = 0.38d;
		} else if (region.equalsIgnoreCase("usa") && clase_cov.equalsIgnoreCase("aliphatics")) {
			resultado = 0.25d;
		}
		return resultado;
	}

	public Double parametroEpsilon(String clase_cov, String region) {
		// TODO Auto-generated method stub
		Double resultado = 0.0d;
		if (region.equalsIgnoreCase("europa") && clase_cov.equalsIgnoreCase("aliphatics")) {
			resultado = 0.16d;
		} else if (region.equalsIgnoreCase("usa") && clase_cov.equalsIgnoreCase("aliphatics")) {
			resultado = 0.18d;
		}
		return resultado;
	}

	public Double parametroP(String covsFotolizan, String region) {
		// TODO Auto-generated method stub
		String[] vectorA = { "aldehydes/ketones", "alpha-dicarbonyls", "cycloketones"};
		Double[] vectorEuropa = { 14.0d, 67.0d, 0.0d, 0.0d};
		Double[] vectorUsa = { 10.0d, 124.0d, 0.0d, 0.0d };
		return buscarParametro(covsFotolizan, region, vectorA, vectorEuropa, vectorUsa);
	}

	public Double parametroE(String covsConOzono, String region) {
		// TODO Auto-generated method stub
		Double resultado = 0.0d;
		if (region.equalsIgnoreCase("europa") && covsConOzono.equalsIgnoreCase("alkenes/unsaturated oxygenates")) {
			resultado = 20.9d;
		} else if (region.equalsIgnoreCase("usa") && covsConOzono.equalsIgnoreCase("alkenes/unsaturated oxygenates")) {
			resultado = 22.0d;
		}
		
		return resultado;
	}

	public Double parametroLambda(String covsConOzono, String region) {
		// TODO Auto-generated method stub
		Double resultado = 0.0d;
		if (region.equalsIgnoreCase("europa") && covsConOzono.equalsIgnoreCase("alkenes/unsaturated oxygenates")) {
			resultado = 0.043d;
		} else if (region.equalsIgnoreCase("usa") && covsConOzono.equalsIgnoreCase("alkenes/unsaturated oxygenates")) {
			resultado = 0.15d;
		}
		return resultado;
	}

	public Double parametroQ(String covAromatico, String region) {
		// TODO Auto-generated method stub
		String[] vectorA = {"benzaldehydes/styrenes", "hydroxyarenes"};
		Double[] vectorEuropa = { 74.0d, 41.0d };
		Double[] vectorUsa = { 80.0d, 0.0d };
		return buscarParametro(covAromatico, region, vectorA, vectorEuropa, vectorUsa);
	}

	public Double buscarParametro(String clase_cov, String region, String[] vectorCov, Double[] vectorEuropa,
			Double[] vectorUsa) {
		Double resultado = 0.0d;
		for (int i = 0; i < vectorCov.length; i++) {
			if (vectorCov[i].equalsIgnoreCase(clase_cov)) {
				if (region.equalsIgnoreCase("europa")) {
					resultado = vectorEuropa[i];
				} else if (region.equalsIgnoreCase("usa")) {
					resultado = vectorUsa[i];
				}
			}
		}
		return resultado;
	}

	public Double calculoGammaS(Integer numeroEnlaces, Double pesoMolecular) {
		String enlacesString = String.valueOf(numeroEnlaces);

		Double enlacesDouble = Double.parseDouble(enlacesString);
		return ((enlacesDouble / 6.0d) * (28.05d / pesoMolecular));

	}

	public Double calculoR(Integer numeroEnlaces, Double constantekoh, String region) {
		String enlacesString = String.valueOf(numeroEnlaces);
		Double enlacesDouble = Double.parseDouble(enlacesString);
		return (1.0d
				- (1.0d / ((constantekoh / 0.0000000000078d) * (6.0d / enlacesDouble) * parametroB(region) + 1.0d)));
	}

	public Double calculoF(String clase_voc, String region, Double constantekoh, Double constantekOhProductoNoReactivo,
			Double rendimientoProductoNoReactivo, Integer numeroEnlaces, Integer numeroEnlacesProducto) {
		if (constantekOhProductoNoReactivo < 2e-12) {
			return 1.0;
		} else {
			return ((numeroEnlaces - (rendimientoProductoNoReactivo * numeroEnlacesProducto)) / numeroEnlaces) * Math
					.pow(0, parametroD(clase_voc, region) * (1 - (1000000000000d * constantekOhProductoNoReactivo / 2)
							* Math.pow(1000000000000d * constantekoh, parametroEpsilon(clase_voc, region))));
		}
	}

	public Double calculoS(Integer numeroCarbonos, String region) {
		Double resultado = -1.0d * parametroC(region) * Math.pow(numeroCarbonos, parametroBeta(region));
		Double resultadoExponencial = Math.exp(resultado);
		Double resultado1 = 1.0d - parametroAlfa(region);
		return resultado1 * resultadoExponencial + parametroAlfa(region);
	}

	public Double calculoRo3(String clase_cov, String region, Double constanteO3, Double rendimientoOh) {
		Double resultadoRo3 = 0.0d;
		Double resultadoE = parametroE(clase_cov, region);
		if (resultadoE != 0.0d) {
			Double resultadoLambda = parametroLambda(clase_cov, region);
			Double miu = Math.pow((constanteO3 / 0.00000000000000000155d) * (rendimientoOh / 0.98d), resultadoLambda);
			return Math.pow(resultadoE, miu);
		} else {
			System.out.println(resultadoRo3);
			return resultadoRo3;
		}

	}

	public Double calcularPocp(String clase_voc, String region, Double pesoMolecular, Double constantekoh,
			Integer numeroEnlaces, Integer numeroCarbonos, Double constantekOhProductoNoReactivo,
			Double rendimientoProductoNoReactivo, Integer numeroEnlacesProducto, String claseCov_p, String covsConOzono, String covAromatico, Double rendimientoCompuestoConOh) {
		Double potencialPocp;
		potencialPocp = (parametroA(clase_voc, region) * calculoGammaS(numeroEnlaces, pesoMolecular)
				* calculoR(numeroEnlaces, constantekoh, region) * calculoS(numeroCarbonos, region)
				* calculoF(clase_voc, region, constantekoh, constantekOhProductoNoReactivo,
						rendimientoProductoNoReactivo, numeroEnlaces, numeroEnlacesProducto))
				+ parametroP(claseCov_p, region)
				+ calculoRo3(covsConOzono, region, constantekOhProductoNoReactivo, rendimientoCompuestoConOh)
				+ parametroQ(covAromatico, region);
		return potencialPocp;

	}
	
	@Transactional(readOnly = true)
	public Ozono buscarPorId(String id) {
		return ozonoRepositorio.getOne(id);
	}
	
	@Transactional(readOnly = true)
	public List<Ozono> listarOzono(){
		List<Ozono> listaRespuesta = new ArrayList();
		listaRespuesta = ozonoRepositorio.findAll();
		return listaRespuesta;
	}
	
	
	
	@Transactional(readOnly = true)
	public List<Double> listarPotencialesPocp(){
		List<Double> listaDePocp = new ArrayList();
		List<Ozono> listarTodos = listarOzono();
		for (Ozono pocp : listarTodos) {
			listaDePocp.add(pocp.getPotencialPocp());
		}
		return listaDePocp;
	}
	
	@Transactional(readOnly = true)
	public List<String> listarNombresDeCompuestos(){
		List<String> listaDeNombres = new ArrayList();
		List<Ozono> listarTodos = listarOzono();
		for (Ozono ozono : listarTodos) {
			listaDeNombres.add(ozono.getNombreCompuesto());
		}
		return listaDeNombres;
	}
	
	@Transactional
	public void eliminarPocp(String id) {
		ozonoRepositorio.deleteById(id);
	}
	
}

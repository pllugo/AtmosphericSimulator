package com.egg.mypkg.servicios;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.egg.mypkg.entidades.Fco2;
import com.egg.mypkg.entidades.Warming;
import com.egg.mypkg.errores.ErrorServicio;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;


@Service
public class WarmingServicio {

	public List<Warming> crearWarming(MultipartFile archivo) throws ErrorServicio {

		if (archivo != null) {
			try (Reader reader = new BufferedReader(new InputStreamReader(archivo.getInputStream()))) {
				CsvToBean<Warming> csvToBean = new CsvToBeanBuilder(reader).withType(Warming.class)
						.withIgnoreLeadingWhiteSpace(true).build();

				// convert `CsvToBean` object to list of users
				
				
				List<Warming> listaWarming = csvToBean.parse();
				List<Double> lista = listarAbsorbancias(listaWarming);
				for (Double double1 : lista) {
					System.out.println(double1.toString());
				}
				
				return listaWarming;
			} catch (Exception ex) {
				// TODO: handle exception
				throw new ErrorServicio(ex.getMessage());
			}
		}
		return null;
	}
	
	
	public List<Double> listarAbsorbancias(List<Warming> listaDeFtir) {
		List<Double> listaAbsorbancia = new ArrayList();
		for (Warming warming : listaDeFtir) {
			listaAbsorbancia.add(warming.getLongitudOnda());
		}
		return listaAbsorbancia;
	}

}

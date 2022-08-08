package com.egg.mypkg.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.egg.mypkg.entidades.Ozono;

@Repository
public interface OzonoRepositorio extends JpaRepository<Ozono, String> {
	
	
}

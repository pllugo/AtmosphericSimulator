package com.egg.mypkg.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.egg.mypkg.entidades.Tiempo;

@Repository
public interface TiempoRepositorio extends JpaRepository<Tiempo, String> {

}

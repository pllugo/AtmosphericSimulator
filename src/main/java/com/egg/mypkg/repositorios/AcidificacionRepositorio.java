package com.egg.mypkg.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.egg.mypkg.entidades.Acidificacion;

@Repository
public interface AcidificacionRepositorio extends JpaRepository<Acidificacion, String> {

}

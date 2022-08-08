package com.egg.mypkg.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.egg.mypkg.entidades.Calentamiento;

@Repository
public interface CalentamientoRepositorio extends JpaRepository<Calentamiento, String> {

}

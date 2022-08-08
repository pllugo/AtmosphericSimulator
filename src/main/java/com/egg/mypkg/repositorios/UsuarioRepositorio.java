package com.egg.mypkg.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.egg.mypkg.entidades.Usuario;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, String> {

	@Query("SELECT u from Usuario u WHERE u.email LIKE :email")
	public Usuario buscarPorEmail(@Param("email") String email);
}

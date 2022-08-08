package com.egg.mypkg.servicios;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.egg.mypkg.entidades.Usuario;
import com.egg.mypkg.enumeraciones.Rol;
import com.egg.mypkg.errores.ErrorServicio;
import com.egg.mypkg.repositorios.UsuarioRepositorio;

@Service
public class UsuarioServicio implements UserDetailsService {
	
	
	@Autowired
	private UsuarioRepositorio usuarioRepositorio;
	
	
	@Transactional
	public void registrarUsuario(String nombre, String email, String password, String password2) throws ErrorServicio {
		validar(nombre, email, password, password2);
		
		Usuario usuario = new Usuario();
		usuario.setNombre(nombre);
		usuario.setEmail(email);
		usuario.setPassword(new BCryptPasswordEncoder().encode(password));
		if (email.equals("m.belen.blanco@unc.edu.ar") || email.equals("pedro.lugo@unc.edu.ar")) {
			usuario.setRol(Rol.ADMIN);
		}else {
			usuario.setRol(Rol.USER);
		}
		usuarioRepositorio.save(usuario);
	}
	
	
	
	public void validar(String nombre, String email, String password, String password2) throws ErrorServicio {
		if (nombre.isEmpty() || nombre == null) {
			throw new ErrorServicio("El nombre no puede ser nulo o estar vacio");
		}
		if (email.isEmpty() || email == null) {
			throw new ErrorServicio("El email no puede ser nulo o estar vacio");
		}
		if (password.isEmpty() || password == null || password2.length() <= 5) {
			throw new ErrorServicio("La contraseña no puede estar vacia, y debe tener más de 5 digitos");
		}
		if (!password.equals(password2)) {
			throw new ErrorServicio("Las contraseñas ingresadas deben ser iguales");
		}
	}

	
	public List<Usuario> listarTodosLosUsuarios(){
		return usuarioRepositorio.findAll();
	}
	

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		Usuario usuario = usuarioRepositorio.buscarPorEmail(email);
		if (usuario != null) {
			
			List<GrantedAuthority> permisos = new ArrayList<>();
			
			GrantedAuthority p = new SimpleGrantedAuthority("ROLE_" + usuario.getRol().toString());
			
			permisos.add(p);
			
			ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
			HttpSession session = attr.getRequest().getSession(true);
			
			session.setAttribute("usuariosession", usuario);
			
			return new org.springframework.security.core.userdetails.User(usuario.getEmail(), usuario.getPassword(), permisos);
		}else {
			return null;
		}
		
	}
}

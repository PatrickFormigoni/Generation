package org.generation.hotel.service;

import java.util.Base64;
import java.util.Optional;

//import org.apache.commons.codec.binary.Base64;
import org.generation.hotel.model.Usuario;
import org.generation.hotel.model.UsuarioLogado;
import org.generation.hotel.model.UsuarioLogin;
import org.generation.hotel.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository  usuarioRepository;
	
	public Usuario CadastrarUsuario(Usuario usuario) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		
		String senhaEncoder = encoder.encode(usuario.getPassword());
		usuario.setPassword(senhaEncoder);
		
		return usuarioRepository.save(usuario);
	}
	
	public Optional<UsuarioLogado> Logar(UsuarioLogin usuarioLogin)
	{
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(); 
		
		Optional<Usuario> usuario = usuarioRepository.findByEmail(usuarioLogin.getEmail());
		
		if (usuario.isPresent() == false) {
			return Optional.empty();
		}
			
		if (encoder.matches(usuarioLogin.getPassword(), usuario.get().getPassword()) == false) {
			return Optional.empty();
		}
		
		// Token Basic
		String auth = usuarioLogin.getEmail() + ":" + usuarioLogin.getPassword();
		
		String encoding = Base64.getEncoder().encodeToString((auth).getBytes());
		
		//byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
		String authHeader = "Basic " + encoding;
		
		UsuarioLogado usuarioLogado = new UsuarioLogado();
		
		usuarioLogado.setCodigo(usuario.get().getCodigo());
		usuarioLogado.setNome(usuario.get().getNome());
		usuarioLogado.setEmail(usuario.get().getEmail());
		usuarioLogado.setToken(authHeader);
		
		return Optional.ofNullable(usuarioLogado);
	}
	
}

package org.generation.hotel.controller;

import java.util.List;
import java.util.Optional;

import org.generation.hotel.model.Usuario;
import org.generation.hotel.model.UsuarioLogado;
import org.generation.hotel.model.UsuarioLogin;
import org.generation.hotel.repository.UsuarioRepository;
import org.generation.hotel.service.SendMail;
import org.generation.hotel.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/usuario")
@CrossOrigin("*")
public class UsuarioController {

	@Autowired
	private UsuarioRepository repository;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private SendMail sendMail;
	
	@PostMapping("/login")
	public ResponseEntity<Object> Logar(@RequestBody UsuarioLogin usuarioLogin)
	{
		Optional<UsuarioLogado> usuarioLogado = usuarioService.Logar(usuarioLogin);
		
 		if (usuarioLogado.isPresent())		
		return ResponseEntity.ok(usuarioLogado.get());
		
 		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Acesso não autorizado.");
	}

	@GetMapping("/corrente")
	public ResponseEntity<Object> Corrente(){
		
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return ResponseEntity.ok(principal);
		
	}
	
	@GetMapping("/email")
	public ResponseEntity<Object> Email()
	{
		sendMail.send("patrick@organizadocs.com.br", "teste abacaxi", "Corpo do e-mail abacaxi!");
		return ResponseEntity.ok("E-mail enviado com sucesso!");		
	}


	@GetMapping
	public ResponseEntity<List<Usuario>> GetAll()
	{
		return ResponseEntity.ok(repository.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Object> GetById(@PathVariable long id)
	{
		Optional<Usuario> usuario;
		usuario = repository.findById(id);
		
		if (usuario.isPresent())
			return ResponseEntity.ok(usuario.get());
		
	
		//throw new UsuarioException("Usuário não encontrado");
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não encontrado");

		//return repository.findById(id).map(resp -> ResponseEntity.ok(resp)).orElse(ResponseEntity.notFound().build());
		
	}
	
	@GetMapping("/nome/{nome}")
	public ResponseEntity<List<Usuario>> GetByNome(@PathVariable String nome){
		return ResponseEntity.ok(repository.findAllByNomeContainingIgnoreCase(nome));
	}
	
	@PostMapping
	public ResponseEntity<Usuario> Post(@RequestBody Usuario usuario){
		
		return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.CadastrarUsuario(usuario));
			
		//return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(usuario));
	}

	@PutMapping
	public ResponseEntity<Usuario> Put(@RequestBody Usuario usuario){
		return ResponseEntity.status(HttpStatus.OK).body(repository.save(usuario));
	}


}

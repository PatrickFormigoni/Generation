package org.generation.hotel.repository;

import java.util.List;
import java.util.Optional;

import org.generation.hotel.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	public List<Usuario> findAllByNomeContainingIgnoreCase(String name);
	
	public Optional<Usuario> findByEmail(String email);
}

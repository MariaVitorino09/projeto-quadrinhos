package senai.projeto_quadrinho.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import senai.projeto_quadrinho.entities.Personagem;
import senai.projeto_quadrinho.services.PersonagemService;

@RestController
@RequestMapping("/api/personagem")
@CrossOrigin(origins = "*")
public class PersonagemController {

	@Autowired
	private PersonagemService service;

	@PostMapping
	public ResponseEntity<Personagem> cadastrar(@Valid @RequestBody Personagem personagem) {
		Personagem personagemSalvo = service.salvar(personagem);
		return ResponseEntity.status(HttpStatus.CREATED).body(personagemSalvo);
	}

	@GetMapping
	public ResponseEntity<List<Personagem>> listarTodos() {
		return ResponseEntity.ok(service.listarTodos());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Personagem> buscarPorId(@PathVariable Long id) {
		return service.buscarPorId(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> atualizar(@PathVariable Long id, @Valid @RequestBody Personagem personagem) {
	    try {
	        Personagem atualizada = service.atualizar(id, personagem);
	        if (atualizada == null) {
	            return ResponseEntity.notFound().build();
	        }
	        return ResponseEntity.ok(atualizada);
	    } catch (IllegalArgumentException e) {
	        return ResponseEntity.badRequest().body(e.getMessage()); 
	    }
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletar(@PathVariable Long id) {
		try {
			service.deletar(id);
			return ResponseEntity.noContent().build();
		} catch (RuntimeException e) {
			return ResponseEntity.notFound().build();
		}
	}

}
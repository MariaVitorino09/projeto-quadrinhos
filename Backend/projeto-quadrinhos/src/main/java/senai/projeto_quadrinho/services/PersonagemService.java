package senai.projeto_quadrinho.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import senai.projeto_quadrinho.entities.Personagem;
import senai.projeto_quadrinho.enums.Alinhamento;
import senai.projeto_quadrinho.repositories.PersonagemRepository;

@Service
public class PersonagemService {

	@Autowired
	private PersonagemRepository repository;

	public List<Personagem> listarTodos() {
		return repository.findAll();
	}

	public Optional<Personagem> buscarPorId(Long id) {
		return repository.findById(id);
	}

	public Personagem salvar(Personagem personagem) {
		validarRegrasDeNegocio(personagem, null);
		return repository.save(personagem);
	}

	public Personagem atualizar(Long id, Personagem personagem) {
	    Optional<Personagem> existente = buscarPorId(id);

	    if (existente.isPresent()) {
	        validarRegrasDeNegocio(personagem, id); 

	        Personagem atualizado = existente.get();
	        atualizado.setNome(personagem.getNome());
	        atualizado.setNivelPoder(personagem.getNivelPoder());
	        atualizado.setEditora(personagem.getEditora());
	        atualizado.setAlinhamento(personagem.getAlinhamento());

	        return repository.save(atualizado);
	    }

	    return null;
	}

	public void deletar(Long id) {

		if (!repository.existsById(id)) {
			throw new RuntimeException("Personagem não encontrado.");
		}
		repository.deleteById(id);
	}
	
	private void validarRegrasDeNegocio(Personagem personagem, Long idExistente) {

		if (idExistente == null) {
			if (repository.existsByNome(personagem.getNome())) {
				throw new IllegalArgumentException(
						"Já existe um personagem cadastrado com o codinome: " + personagem.getNome());
			}
		} else {
			if (repository.existsByNomeAndIdNot(personagem.getNome(), idExistente)) {
				throw new IllegalArgumentException(
						"O codinome '" + personagem.getNome() + "' já está em uso por outro personagem.");
			}
		}

		if (personagem.getNivelPoder() == null || personagem.getNivelPoder() < 1 || personagem.getNivelPoder() > 100) {
			throw new IllegalArgumentException("O Nível de Ameaça/Poder deve ser entre 1 e 100.");
		}

		if (personagem.getAlinhamento() != null) {
			boolean isVilao = personagem.getAlinhamento() == Alinhamento.VILAO
					|| "VILAO".equalsIgnoreCase(personagem.getAlinhamento().name())
					|| "VILÃO".equalsIgnoreCase(personagem.getAlinhamento().name());

			if (isVilao && personagem.getNivelPoder() < 50) {
				throw new IllegalArgumentException(
						"Personagens com alinhamento 'Vilão' não podem ter Nível de Ameaça/Poder inferior a 50.");
			}
		}
	}
}

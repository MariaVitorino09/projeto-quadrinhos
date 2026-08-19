package senai.projeto_quadrinho.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import senai.projeto_quadrinho.entities.Personagem;

@Repository
public interface PersonagemRepository extends JpaRepository<Personagem, Long> {
    
    boolean existsByNome(String nome);
    
    boolean existsByNomeAndIdNot(String nome, Long id);
}
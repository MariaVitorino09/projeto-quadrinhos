package senai.projeto_quadrinho.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import senai.projeto_quadrinho.enums.Alinhamento;
import senai.projeto_quadrinho.enums.Editora;

@Entity
@Table(name = "tb_personagens")
public class Personagem {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "alinhamento")
	private Alinhamento alinhamento;	
	
	@Enumerated(EnumType.STRING)
	@Column(name = "editora")
	private Editora editora;	
	
	@NotBlank(message = "O Codinome do personagem não pode ficar em branco.")
	@Column(name = "nome" )
	private String nome;
	
	@NotNull(message = "O nível de poder é obrigatório")
	@Min(value = 1, message = "O nível de poder/ameaça não pode ser menor que 1")
	@Max(value = 100, message = "O nível de poder/ameaça não pode ser maior que 100")
	@Column(name = "nivel_poder", nullable = false)
	private Integer nivelPoder;
	
	public Personagem() {}
	
	public Personagem(String nome, Integer nivelPoder, Editora editora, Alinhamento alinhamento) {
		this.nome = nome;
		this.nivelPoder = nivelPoder;
		this.editora = editora;
		this.alinhamento = alinhamento;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Alinhamento getAlinhamento() {
		return alinhamento;
	}

	public void setAlinhamento(Alinhamento alinhamento) {
		this.alinhamento = alinhamento;
	}

	public Editora getEditora() {
		return editora;
	}

	public void setEditora(Editora editora) {
		this.editora = editora;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getNivelPoder() {
		return nivelPoder;
	}

	public void setNivelPoder(Integer nivelPoder) {
		this.nivelPoder = nivelPoder;
	}
	
	

}

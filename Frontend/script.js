// URL exata do Backend
const API_URL = "http://localhost:8080/api/personagem";

const form = document.getElementById("personagemForm");
const inputId = document.getElementById("personagemId");
const inputNome = document.getElementById("nome");
const inputNivelPoder = document.getElementById("nivelPoder");
const selectEditora = document.getElementById("editora");
const selectAlinhamento = document.getElementById("alinhamento");
const tabelaBody = document.getElementById("tabelaPersonagens");
const divMensagem = document.getElementById("mensagem");
const btnCancelar = document.getElementById("btnCancelar");

// Carrega a lista ao abrir a página (CT02)
document.addEventListener("DOMContentLoaded", carregarPersonagens);

// Evento de salvar (CT01 e CT03)
form.addEventListener("submit", async (e) => {
    e.preventDefault();

    const personagem = {
        nome: inputNome.value.trim(),
        nivelPoder: parseInt(inputNivelPoder.value),
        editora: selectEditora.value,
        alinhamento: selectAlinhamento.value
    };

    const id = inputId.value;
    const url = id ? `${API_URL}/${id}` : API_URL;
    const method = id ? "PUT" : "POST";

    try {
        const response = await fetch(url, {
            method: method,
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(personagem)
        });

        if (!response.ok) {
            // Trata mensagens de erro da API para cobrir CT05, CT06 e CT07
            let mensagemErro = "Erro ao processar requisição.";
            const textoResposta = await response.text();

            try {
                const jsonErro = JSON.parse(textoResposta);
                mensagemErro = jsonErro.message || jsonErro.erro || textoResposta;
            } catch {
                mensagemErro = textoResposta;
            }

            mostrarMensagem(mensagemErro, true);
        } else {
            mostrarMensagem(`Personagem ${id ? 'atualizado' : 'cadastrado'} com sucesso!`, false);
            limparFormulario();
            carregarPersonagens();
        }
    } catch (error) {
        mostrarMensagem("Erro ao se conectar com a API.", true);
    }
});

// Listar personagens (CT02)
async function carregarPersonagens() {
    try {
        const response = await fetch(API_URL);
        if (!response.ok) throw new Error();
        
        const personagens = await response.json();
        tabelaBody.innerHTML = "";

        personagens.forEach((p) => {
            const tr = document.createElement("tr");
            tr.innerHTML = `
                <td>${p.id}</td>
                <td><strong>${p.nome}</strong></td>
                <td>${p.nivelPoder}</td>
                <td>${p.editora}</td>
                <td>${p.alinhamento}</td>
                <td>
                    <button class="btn-edit" onclick="prepararEdicao(${p.id}, '${p.nome}', ${p.nivelPoder}, '${p.editora}', '${p.alinhamento}')">Editar</button>
                    <button class="btn-delete" onclick="deletarPersonagem(${p.id})">Excluir</button>
                </td>
            `;
            tabelaBody.appendChild(tr);
        });
    } catch (error) {
        console.error("Erro ao carregar lista de personagens:", error);
    }
}

// Preparar para edição (CT03)
function prepararEdicao(id, nome, nivelPoder, editora, alinhamento) {
    inputId.value = id;
    inputNome.value = nome;
    inputNivelPoder.value = nivelPoder;
    selectEditora.value = editora;
    selectAlinhamento.value = alinhamento;

    document.getElementById("btnSalvar").innerText = "Atualizar Personagem";
    btnCancelar.classList.remove("hidden");
}

// Excluir personagem (CT04)
async function deletarPersonagem(id) {
    if (confirm("Tem certeza que deseja remover este personagem?")) {
        try {
            const response = await fetch(`${API_URL}/${id}`, { method: "DELETE" });
            if (response.ok) {
                mostrarMensagem("Personagem excluído com sucesso!", false);
                carregarPersonagens();
            } else {
                mostrarMensagem("Erro ao excluir personagem.", true);
            }
        } catch (error) {
            mostrarMensagem("Erro na conexão com o servidor.", true);
        }
    }
}

// Limpar formulário
function limparFormulario() {
    inputId.value = "";
    form.reset();
    document.getElementById("btnSalvar").innerText = "Salvar Personagem";
    btnCancelar.classList.add("hidden");
}

// Exibir mensagens (Exibe os bloqueios dos casos de teste na tela)
function mostrarMensagem(texto, isErro) {
    divMensagem.innerText = texto;
    divMensagem.className = `mensagem ${isErro ? 'erro' : 'sucesso'}`;
    divMensagem.classList.remove("hidden");

    setTimeout(() => {
        divMensagem.className = "mensagem hidden";
    }, 5000);
}
import { apiRequest } from "./api.js";

let alunoIdParaDeletar = null;
let todosAlunos = [];

function showToast(message, type = "success") {
    const toastEl = document.getElementById("toast");
    const messageEl = document.getElementById("toast-message");

    messageEl.textContent = message;
    toastEl.className = `toast align-items-center text-white bg-${type} border-0`;

    const toast = new bootstrap.Toast(toastEl);
    toast.show();
}

async function carregarAlunos() {
    const tabela = document.getElementById("tabela-alunos");

    tabela.innerHTML = `
        <tr>
            <td colspan="5">Carregando...</td>
        </tr>
    `;

    try {
        todosAlunos = await apiRequest("/alunos");
        renderizarTabela(todosAlunos);
    } catch (error) {
        tabela.innerHTML = `
            <tr>
                <td colspan="5">Erro ao carregar dados</td>
            </tr>
        `;
        showToast(error.message, "danger");
    }
}

function renderizarTabela(lista) {
    const tabela = document.getElementById("tabela-alunos");

    if (lista.length === 0) {
        tabela.innerHTML = `
            <tr>
                <td colspan="5">Nenhum aluno encontrado</td>
            </tr>
        `;
        return;
    }

    tabela.innerHTML = lista.map(aluno => `
        <tr>
            <td>${aluno.nome}</td>
            <td>${aluno.eol}</td>
            <td>${aluno.turma}</td>
            <td>${aluno.tabletNs ?? "-"}</td>
            <td>
                <i class="bi bi-eye text-primary mx-1"
                   style="cursor:pointer"
                   onclick="visualizar(${aluno.id})"></i>

                <i class="bi bi-pencil text-warning mx-1"
                   style="cursor:pointer"
                   onclick="editar(${aluno.id})"></i>

                <i class="bi bi-trash text-danger mx-1"
                   style="cursor:pointer"
                   onclick="deletar(${aluno.id})"></i>
            </td>
        </tr>
    `).join("");
}

function filtrarAlunos(termo) {
    const termoBusca = termo.toLowerCase().trim();
    
    const filtrados = todosAlunos.filter(a => 
        (a.nome && a.nome.toLowerCase().includes(termoBusca)) || 
        (a.eol && a.eol.toLowerCase().includes(termoBusca)) ||
        (a.turma && a.turma.toLowerCase().includes(termoBusca))
    );

    renderizarTabela(filtrados);
}

function visualizar(id) {
    window.location.href = `aluno-view.html?id=${id}`;
}

function editar(id) {
    window.location.href = `aluno-edit.html?id=${id}`;
}

function deletar(id) {
    alunoIdParaDeletar = id;

    const modal = new bootstrap.Modal(document.getElementById("modalDelete"));
    modal.show();
}

document.getElementById("btnConfirmDelete").addEventListener("click", async () => {
    try {
        await apiRequest(`/alunos/${alunoIdParaDeletar}`, {
            method: "DELETE"
        });

        showToast("Aluno deletado com sucesso", "success");
        carregarAlunos();

    } catch (error) {
        showToast(error.message, "danger");
    }

    const modal = bootstrap.Modal.getInstance(document.getElementById("modalDelete"));
    modal.hide();
});

window.visualizar = visualizar;
window.editar = editar;
window.deletar = deletar;

document.addEventListener("DOMContentLoaded", () => {
    carregarAlunos();
    
    const inputBusca = document.getElementById("busca");
    if (inputBusca) {
        inputBusca.addEventListener("input", (e) => filtrarAlunos(e.target.value));
    }
});
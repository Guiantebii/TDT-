import { apiRequest } from "./api.js";

let alunoIdParaDeletar = null;

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
        const alunos = await apiRequest("/alunos");

        if (alunos.length === 0) {
            tabela.innerHTML = `
                <tr>
                    <td colspan="5">Nenhum aluno encontrado</td>
                </tr>
            `;
            return;
        }

        tabela.innerHTML = alunos.map(aluno => `
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

    } catch (error) {
        tabela.innerHTML = `
            <tr>
                <td colspan="5">Erro ao carregar dados</td>
            </tr>
        `;
        showToast(error.message, "danger");
    }
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

window.onload = carregarAlunos;
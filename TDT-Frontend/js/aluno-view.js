import { apiRequest } from "./api.js";

const params = new URLSearchParams(window.location.search);
const id = params.get("id");

function showToast(message, type = "danger") {
    const toastEl = document.getElementById("toast");
    const messageEl = document.getElementById("toast-message");

    messageEl.textContent = message;
    toastEl.className = `toast align-items-center text-white bg-${type} border-0`;

    const toast = new bootstrap.Toast(toastEl);
    toast.show();
}

async function carregar() {

    if (!id) {
        showToast("ID do aluno não informado", "danger");

        setTimeout(() => {
            window.location.href = "alunos.html";
        }, 1500);

        return;
    }

    try {
        const aluno = await apiRequest(`/alunos/${id}`);

        document.getElementById("nome").textContent = aluno.nome;
        document.getElementById("eol").textContent = aluno.eol;
        document.getElementById("turma").textContent = aluno.turma;
        document.getElementById("tel1").textContent = aluno.tel1 || "-";
        document.getElementById("tel2").textContent = aluno.tel2 || "-";

        const data = new Date(aluno.dataNasc);
        document.getElementById("dataNasc").textContent =
            data.toLocaleDateString("pt-BR");

    } catch (error) {

        
        document.querySelector(".card").innerHTML = `
            <div class="text-center p-5">
                <i class="bi bi-exclamation-triangle text-danger fs-1"></i>
                <h4 class="mt-3">Erro ao carregar aluno</h4>
                <p class="text-muted">${error.message}</p>

                <button class="btn btn-primary mt-3"
                        onclick="window.location.href='alunos.html'">
                    Voltar
                </button>
            </div>
        `;
        showToast(error.message, "danger");
    }
}

function editarAluno() {
    window.location.href = `aluno-edit.html?id=${id}`;
}

window.editarAluno = editarAluno;
carregar();

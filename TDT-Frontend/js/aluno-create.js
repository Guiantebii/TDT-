import { apiRequest } from "./api.js";
import { limparErros, mostrarErrosCampo } from "./form.js";

// 🔥 Toast
function showToast(message, type = "success") {
    const toastEl = document.getElementById("toast");
    const messageEl = document.getElementById("toast-message");

    messageEl.textContent = message;
    toastEl.className = `toast align-items-center text-white bg-${type} border-0`;

    const toast = new bootstrap.Toast(toastEl);
    toast.show();
}

async function salvar() {

    limparErros();

    const btn = document.getElementById("btnSalvar");
    btn.disabled = true;
    btn.innerHTML = "Salvando...";

    const aluno = {
        nome: document.getElementById("nome").value,
        eol: document.getElementById("eol").value,
        turma: document.getElementById("turma").value,
        tel1: document.getElementById("tel1").value,
        tel2: document.getElementById("tel2").value,
        dataNasc: document.getElementById("dataNasc").value
    };

    try {
        await apiRequest("/alunos", {
            method: "POST",
            body: JSON.stringify(aluno)
        });

        showToast("Aluno criado com sucesso", "success");

        setTimeout(() => {
            window.location.href = "alunos.html";
        }, 1500);

    } catch (error) {

        if (error.validation) {
            mostrarErrosCampo(error.validation);
        } else {
            showToast(error.message, "danger");
        }

    } finally {
        btn.disabled = false;
        btn.innerHTML = `<i class="bi bi-check-lg"></i> Salvar`;
    }
}

document.getElementById("btnSalvar").addEventListener("click", salvar);
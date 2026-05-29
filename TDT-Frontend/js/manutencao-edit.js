import { apiRequest } from "./api.js";
import { limparErros, mostrarErrosCampo } from "./form.js";

const params = new URLSearchParams(window.location.search);
const id = params.get("id");

function showToast(message, type = "success") {
    const toastEl = document.getElementById("toast");
    const messageEl = document.getElementById("toast-message");

    messageEl.textContent = message;
    toastEl.className = `toast align-items-center text-white bg-${type} border-0`;
    new bootstrap.Toast(toastEl).show();
}

async function carregarManutencao() {
    if (!id) {
        showToast("ID inválido", "danger");
        return;
    }

    try {
        const manutencao = await apiRequest(`/manutencoes/${id}`);

        document.getElementById("tc_id").value = manutencao.tc_id;
        document.getElementById("descricao").value = manutencao.descricao;

    } catch (e) {
        showToast("Erro ao carregar manutenção", "danger");
    }
}

async function salvar() {
    limparErros();

    const tcIdValue = document.getElementById("tc_id").value;
    if (!tcIdValue) {
        showToast("Por favor, insira o ID do vínculo", "danger");
        return;
    }

    const btn = document.getElementById("btnSalvar");
    btn.disabled = true;
    btn.innerHTML = "Atualizando...";

    const manutencao = {
        tc_id: parseInt(tcIdValue),
        descricao: document.getElementById("descricao").value
    };

    try {
        await apiRequest(`/manutencoes/${id}`, {
            method: "PUT",
            body: JSON.stringify(manutencao)
        });

        showToast("Atualizado com sucesso", "success");

        setTimeout(() => {
            window.location.href = "manutencoes.html";
        }, 1200);

    } catch (error) {
        if (error.validation) {
            mostrarErrosCampo(error.validation);
        } else {
            showToast(error.message, "danger");
        }
    } finally {
        btn.disabled = false;
        btn.innerHTML = `<i class="bi bi-check-lg"></i> Atualizar`;
    }
}

document.getElementById("btnSalvar").addEventListener("click", salvar);

document.addEventListener("DOMContentLoaded", () => {
    carregarManutencao();
});
import { apiRequest } from "./api.js";
import { limparErros, mostrarErrosCampo } from "./form.js";

function showToast(message, type = "success") {
    const toastEl = document.getElementById("toast");
    const messageEl = document.getElementById("toast-message");

    messageEl.textContent = message;
    toastEl.className = `toast align-items-center text-white bg-${type} border-0`;
    new bootstrap.Toast(toastEl).show();
}

async function carregarSelects() {
    try {
        const tablets = await apiRequest("/tablets");
        const alunos = await apiRequest("/alunos"); // Presumindo que você tenha essa rota!

        const selectTab = document.getElementById("tab_id");
        selectTab.innerHTML = `<option value="">Selecione um Tablet</option>` + 
            tablets.map(t => `<option value="${t.id}">IMEI: ${t.imei} - NS: ${t.ns}</option>`).join("");

        const selectAlu = document.getElementById("alu_id");
        selectAlu.innerHTML = `<option value="">Selecione um Aluno</option>` + 
            alunos.map(a => `<option value="${a.id}">${a.nome || a.id}</option>`).join("");

    } catch (error) {
        showToast("Erro ao carregar dados para os selects", "danger");
    }
}

async function salvar() {
    limparErros();

    const btn = document.getElementById("btnSalvar");
    btn.disabled = true;
    btn.innerHTML = "Salvando...";

    const devolucao = {
        tab_id: parseInt(document.getElementById("tab_id").value),
        alu_id: parseInt(document.getElementById("alu_id").value),
        dataEntrega: document.getElementById("dataEntrega").value || null,
        dataDevolucao: document.getElementById("dataDevolucao").value || null
    };

    try {
        await apiRequest("/devolucoes", {
            method: "POST",
            body: JSON.stringify(devolucao)
        });

        showToast("Devolução registrada com sucesso!", "success");

        setTimeout(() => {
            window.location.href = "devolucoes.html";
        }, 1200);

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

document.addEventListener("DOMContentLoaded", () => {
    carregarSelects();
});
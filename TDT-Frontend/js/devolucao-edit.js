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

async function carregarSelects() {
    try {
        const tablets = await apiRequest("/tablets");
        const alunos = await apiRequest("/alunos");

        const selectTab = document.getElementById("tab_id");
        selectTab.innerHTML = `<option value="">Selecione um Tablet</option>` + 
            tablets.map(t => `<option value="${t.id}">IMEI: ${t.imei} - NS: ${t.ns}</option>`).join("");

        const selectAlu = document.getElementById("alu_id");
        selectAlu.innerHTML = `<option value="">Selecione um Aluno</option>` + 
            alunos.map(a => `<option value="${a.id}">${a.nome || a.id}</option>`).join("");

    } catch (error) {
        showToast("Erro ao carregar dados", "danger");
    }
}

async function carregarDevolucao() {
    if (!id) {
        showToast("ID inválido", "danger");
        return;
    }

    try {

        await carregarSelects(); 
        
        const devolucao = await apiRequest(`/devolucoes/${id}`);

        document.getElementById("tab_id").value = devolucao.tab_id;
        document.getElementById("alu_id").value = devolucao.alu_id;
        document.getElementById("dataEntrega").value = devolucao.dataEntrega;
        document.getElementById("dataDevolucao").value = devolucao.dataDevolucao;

    } catch (e) {
        showToast("Erro ao carregar devolução", "danger");
    }
}

async function salvar() {
    limparErros();

    const btn = document.getElementById("btnSalvar");
    btn.disabled = true;
    btn.innerHTML = "Atualizando...";

    const devolucao = {
        tab_id: parseInt(document.getElementById("tab_id").value),
        alu_id: parseInt(document.getElementById("alu_id").value),
        dataEntrega: document.getElementById("dataEntrega").value || null,
        dataDevolucao: document.getElementById("dataDevolucao").value || null
    };

    try {
        await apiRequest(`/devolucoes/${id}`, {
            method: "PUT",
            body: JSON.stringify(devolucao)
        });

        showToast("Atualizado com sucesso", "success");

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
        btn.innerHTML = `<i class="bi bi-check-lg"></i> Atualizar`;
    }
}

document.getElementById("btnSalvar").addEventListener("click", salvar);

document.addEventListener("DOMContentLoaded", () => {
    carregarDevolucao();
});
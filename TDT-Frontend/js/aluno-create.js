import { apiRequest } from "./api.js";
import { limparErros, mostrarErrosCampo } from "./form.js";

let todosTablets = [];

function showToast(message, type = "success") {
    const toastEl = document.getElementById("toast");
    const messageEl = document.getElementById("toast-message");

    messageEl.textContent = message;
    toastEl.className = `toast align-items-center text-white bg-${type} border-0`;

    const toast = new bootstrap.Toast(toastEl);
    toast.show();
}

async function carregarTablets() {
    try {
        todosTablets = await apiRequest("/tablets");
        renderizarListaTablets(todosTablets);
    } catch (error) {
        showToast("Erro ao carregar tablets", "danger");
    }
}

function renderizarListaTablets(lista) {
    const container = document.getElementById("itensTablet");
    container.innerHTML = "";

    if (lista.length === 0) {
        container.innerHTML = '<li class="dropdown-item text-muted small">Nenhum tablet encontrado</li>';
        return;
    }

    lista.forEach(t => {
        const li = document.createElement("li");
        li.className = "dropdown-item";
        li.style.cursor = "pointer";
        li.textContent = `IMEI: ${t.imei} - NS: ${t.ns}`;
        li.addEventListener("click", () => selecionarTablet(t.id, `IMEI: ${t.imei} - NS: ${t.ns}`));
        container.appendChild(li);
    });
}

function filtrarTablets(termo) {
    const termoBusca = termo.toLowerCase().trim();
    const filtrados = todosTablets.filter(t =>
        (t.imei && t.imei.toLowerCase().includes(termoBusca)) ||
        (t.ns && t.ns.toLowerCase().includes(termoBusca))
    );
    renderizarListaTablets(filtrados);
}

function selecionarTablet(id, texto) {
    document.getElementById("tablet").value = id;
    document.getElementById("comboTablet").textContent = texto;
}

async function salvar() {
    limparErros();

    const btn = document.getElementById("btnSalvar");
    btn.disabled = true;
    btn.innerHTML = "Salvando...";

    const tabletId = document.getElementById("tablet").value;

    if (!tabletId) {
        showToast("Selecione um tablet", "danger");
        btn.disabled = false;
        btn.innerHTML = `<i class="bi bi-check-lg"></i> Salvar`;
        return;
    }

    const aluno = {
        nome: document.getElementById("nome").value,
        eol: document.getElementById("eol").value,
        turma: document.getElementById("turma").value,
        tel1: document.getElementById("tel1").value,
        tel2: document.getElementById("tel2").value,
        dataNasc: document.getElementById("dataNasc").value,
        tabletId: parseInt(tabletId)
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

window.addEventListener("DOMContentLoaded", () => {
    carregarTablets();
    
    const inputBusca = document.getElementById("buscaTablet");
    if (inputBusca) {
        inputBusca.addEventListener("input", (e) => filtrarTablets(e.target.value));
        inputBusca.addEventListener("click", (e) => e.stopPropagation());
    }
});

document.getElementById("btnSalvar").addEventListener("click", salvar);
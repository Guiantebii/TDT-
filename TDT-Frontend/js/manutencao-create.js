import { apiRequest } from "./api.js";
import { limparErros, mostrarErrosCampo } from "./form.js";

let todosVinculos = []; 

function showToast(message, type = "success") {
    const toastEl = document.getElementById("toast");
    const messageEl = document.getElementById("toast-message");
    messageEl.textContent = message;
    toastEl.className = `toast align-items-center text-white bg-${type} border-0`;
    new bootstrap.Toast(toastEl).show();
}

async function carregarVinculos() {
    try {
        todosVinculos = await apiRequest("/tablets-chips");
    } catch (error) {
        showToast("Erro ao carregar lista de equipamentos vinculados.", "danger");
    }
}

function filtrarVinculos(termo) {
    const listaResultados = document.getElementById("lista-resultados");
    
    if (!termo.trim()) {
        listaResultados.style.display = "none";
        return;
    }

    const termoFiltrado = termo.toLowerCase();
    

    const filtrados = todosVinculos.filter(v => 
        (v.tabletImei && v.tabletImei.toLowerCase().includes(termoFiltrado)) ||
        (v.tabletNs && v.tabletNs.toLowerCase().includes(termoFiltrado)) ||
        (v.chipIccid && v.chipIccid.toLowerCase().includes(termoFiltrado))
    );

    if (filtrados.length === 0) {
        listaResultados.innerHTML = `<div class="list-group-item text-muted small">Nenhum equipamento correspondente encontrado</div>`;
        listaResultados.style.display = "block";
        return;
    }

    listaResultados.innerHTML = filtrados.map(v => `
        <button type="button" class="list-group-item list-group-item-action d-flex justify-content-between align-items-center item-selecao" data-id="${v.id}">
            <div>
                <strong class="text-dark">Tablet:</strong> ${v.tabletImei} <span class="text-muted">(${v.tabletNs})</span><br>
                <strong class="text-secondary">Chip ICCID:</strong> ${v.chipIccid}
            </div>
            <span class="badge bg-primary rounded-pill">ID Vínculo: #${v.id}</span>
        </button>
    `).join("");

    listaResultados.style.display = "block";
}

document.getElementById("lista-resultados").addEventListener("click", (e) => {
    const botao = e.target.closest(".item-selecao");
    if (!botao) return;

    const idVinculo = botao.dataset.id;
    const textoCompleto = botao.querySelector("div").innerText.replace(/\n/g, " | ");


    document.getElementById("busca-vinculo").value = `Vínculo #${idVinculo} - ${textoCompleto}`;
    document.getElementById("tc_id").value = idVinculo;
    document.getElementById("lista-resultados").style.display = "none";
});


async function salvar() {
    limparErros();

    const tcIdValue = document.getElementById("tc_id").value;
    if (!tcIdValue) {
        showToast("Por favor, pesquise e selecione um equipamento válido na lista.", "danger");
        return;
    }

    const btn = document.getElementById("btnSalvar");
    btn.disabled = true;
    btn.innerHTML = "Salvando...";

    const manutencao = {
        tc_id: parseInt(tcIdValue),
        descricao: document.getElementById("descricao").value
    };

    try {
        await apiRequest("/manutencoes", {
            method: "POST",
            body: JSON.stringify(manutencao)
        });

        showToast("Manutenção registrada com sucesso!", "success");
        setTimeout(() => { window.location.href = "manutencoes.html"; }, 1200);

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


document.addEventListener("DOMContentLoaded", () => {
    carregarVinculos();

    const inputBusca = document.getElementById("busca-vinculo");

    inputBusca.addEventListener("input", (e) => filtrarVinculos(e.target.value));


    document.addEventListener("click", (e) => {
        if (!e.target.closest("#busca-vinculo") && !e.target.closest("#lista-resultados")) {
            document.getElementById("lista-resultados").style.display = "none";
        }
    });
});

document.getElementById("btnSalvar").addEventListener("click", salvar);
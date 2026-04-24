import { apiRequest } from "./api.js";
import { limparErros, mostrarErrosCampo } from "./form.js";

const params = new URLSearchParams(window.location.search);
const id = params.get("id");

let modalChip;

function showToast(message, type = "success") {
    const el = document.getElementById("toast");
    el.className = `toast show bg-${type}`;
    el.textContent = message;

    setTimeout(() => el.classList.remove("show"), 3000);
}

async function carregarTablet() {

    if (!id) {
        showToast("ID inválido", "danger");
        return;
    }

    try {
        const tablet = await apiRequest(`/tablets/${id}`);

        document.getElementById("imei").value = tablet.imei;
        document.getElementById("ns").value = tablet.ns;

        document.getElementById("chipIccid").textContent =
            tablet.chipIccid || "-";

        document.getElementById("chipStatus").textContent =
            tablet.chipStatus || "-";

    } catch (e) {
        showToast("Erro ao carregar tablet", "danger");
    }
}

async function salvar() {

    limparErros();

    const btn = document.getElementById("btnSalvar");
    btn.disabled = true;
    btn.innerHTML = "Atualizando...";

    const tablet = {
        imei: document.getElementById("imei").value,
        ns: document.getElementById("ns").value
    };

    try {
        await apiRequest(`/tablets/${id}`, {
            method: "PUT",
            body: JSON.stringify(tablet)
        });

        showToast("Atualizado com sucesso");

        setTimeout(() => {
            window.location.href = "tablets.html";
        }, 1200);

    } catch (error) {

        if (error.validation) {
            mostrarErrosCampo(error.validation);
        } else {
            showToast(error.message, "danger");
        }

    } finally {
        btn.disabled = false;
        btn.innerHTML = "Atualizar";
    }
}

async function abrirModalChip() {

    const select = document.getElementById("selectChip");
    select.innerHTML = "Carregando...";

    try {
        const chips = await apiRequest("/chips");

        select.innerHTML = chips.map(c =>
            `<option value="${c.id}">${c.iccid} - ${c.status}</option>`
        ).join("");

        modalChip.show();

    } catch {
        showToast("Erro ao carregar chips", "danger");
    }
}

async function vincularChip() {

    const chipId = document.getElementById("selectChip").value;

    try {
        await apiRequest(`/tablets/${id}/vincular-chip`, {
            method: "POST",
            body: JSON.stringify({ chipId: parseInt(chipId) })
        });

        showToast("Chip atualizado!");
        modalChip.hide();

        carregarTablet();

    } catch {
        showToast("Erro ao trocar chip", "danger");
    }
}

document.getElementById("btnSalvar").addEventListener("click", salvar);
document.getElementById("btnTrocarChip").addEventListener("click", abrirModalChip);
document.getElementById("btnVincularChip").addEventListener("click", vincularChip);

document.addEventListener("DOMContentLoaded", () => {
    modalChip = new bootstrap.Modal(document.getElementById("modalChip"));
    carregarTablet();
});
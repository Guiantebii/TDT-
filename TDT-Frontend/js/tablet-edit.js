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

async function carregarTablet() {

    if (!id) {
        showToast("ID do tablet não informado", "danger");
        setTimeout(() => window.location.href = "tablets.html", 1500);
        return;
    }

    try {
        const tablet = await apiRequest(`/tablets/${id}`);

        document.getElementById("imei").value = tablet.imei;
        document.getElementById("ns").value = tablet.ns;

    } catch (error) {
        showToast(error.message, "danger");
        setTimeout(() => window.location.href = "tablets.html", 1500);
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

        showToast("Tablet atualizado com sucesso", "success");

        setTimeout(() => {
            window.location.href = "tablets.html";
        }, 1500);

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
window.onload = carregarTablet;
import { apiRequest } from "./api.js";
import { limparErros, mostrarErrosCampo } from "./form.js";

const params = new URLSearchParams(window.location.search);
const id = params.get("id");

function showToast(message, type = "success") {
    const toastEl = document.getElementById("toast");
    const messageEl = document.getElementById("toast-message");

    messageEl.textContent = message;
    toastEl.className = `toast align-items-center text-white bg-${type} border-0`;

    const toast = new bootstrap.Toast(toastEl);
    toast.show();
}

async function carregarChip() {

    if (!id) {
        showToast("ID do chip não informado", "danger");
        setTimeout(() => window.location.href = "chips.html", 1500);
        return;
    }

    try {
        const chip = await apiRequest(`/chips/${id}`);

        document.getElementById("iccid").value = chip.iccid;
        document.getElementById("status").value = chip.status;
        document.getElementById("pin").value = chip.pin || "";
        document.getElementById("pin2").value = chip.pin2 || "";
        document.getElementById("puk").value = chip.puk || "";
        document.getElementById("puk2").value = chip.puk2 || "";

    } catch (error) {
        showToast(error.message, "danger");
        setTimeout(() => window.location.href = "chips.html", 1500);
    }
}

async function salvar() {

    limparErros();

    const btn = document.getElementById("btnSalvar");
    btn.disabled = true;
    btn.innerHTML = "Atualizando...";

    const chip = {
        iccid: document.getElementById("iccid").value,
        status: document.getElementById("status").value,
        pin: document.getElementById("pin").value,
        pin2: document.getElementById("pin2").value,
        puk: document.getElementById("puk").value,
        puk2: document.getElementById("puk2").value
    };

    try {
        await apiRequest(`/chips/${id}`, {
            method: "PUT",
            body: JSON.stringify(chip)
        });

        showToast("Chip atualizado com sucesso", "success");

        setTimeout(() => {
            window.location.href = "chips.html";
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
window.onload = carregarChip;
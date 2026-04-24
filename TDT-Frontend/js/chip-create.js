import { apiRequest } from "./api.js";
import { limparErros, mostrarErrosCampo } from "./form.js";

function showToast(message, type = "success") {
    const toastEl = document.getElementById("toast");
    const messageEl = document.getElementById("toast-message");

    messageEl.textContent = message;
    toastEl.className = `toast align-items-center text-white bg-${type} border-0`;

    new bootstrap.Toast(toastEl).show();
}

async function salvar() {

    limparErros();

    const btn = document.getElementById("btnSalvar");
    btn.disabled = true;
    btn.innerHTML = "Salvando...";

    const chip = {
        iccid: document.getElementById("iccid").value,
        status: document.getElementById("status").value,
        pin: document.getElementById("pin").value,
        pin2: document.getElementById("pin2").value,
        puk: document.getElementById("puk").value,
        puk2: document.getElementById("puk2").value
    };

    try {
        await apiRequest("/chips", {
            method: "POST",
            body: JSON.stringify(chip)
        });

        showToast("Chip criado com sucesso", "success");

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
        btn.innerHTML = `<i class="bi bi-check-lg"></i> Salvar`;
    }
}

document.getElementById("btnSalvar").addEventListener("click", salvar);
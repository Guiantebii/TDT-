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

    const tablet = {
        imei: document.getElementById("imei").value,
        ns: document.getElementById("ns").value
    };

    try {
        const novoTablet = await apiRequest("/tablets", {
            method: "POST",
            body: JSON.stringify(tablet)
        });

        showToast("Tablet criado! Agora vincule um chip", "success");

        // 🔥 REDIRECIONA JÁ COM ID
        setTimeout(() => {
            window.location.href = `tablets.html?vincular=${novoTablet.id}`;
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
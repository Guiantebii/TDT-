import { apiRequest } from "./api.js";

const params = new URLSearchParams(window.location.search);
const id = params.get("id");

function showToast(message, type = "danger") {
    const toastEl = document.getElementById("toast");
    const messageEl = document.getElementById("toast-message");

    messageEl.textContent = message;
    toastEl.className = `toast align-items-center text-white bg-${type} border-0`;

    new bootstrap.Toast(toastEl).show();
}

async function carregar() {

    if (!id) {
        showToast("ID do chip não informado", "danger");

        setTimeout(() => {
            window.location.href = "chips.html";
        }, 1500);

        return;
    }

    try {
        const chip = await apiRequest(`/chips/${id}`);

        document.getElementById("iccid").textContent = chip.iccid;
        document.getElementById("status").textContent = chip.status;
        document.getElementById("pin").textContent = chip.pin || "-";
        document.getElementById("pin2").textContent = chip.pin2 || "-";
        document.getElementById("puk").textContent = chip.puk || "-";
        document.getElementById("puk2").textContent = chip.puk2 || "-";

    } catch (error) {

        document.querySelector(".card").innerHTML = `
            <div class="text-center p-5">
                <i class="bi bi-exclamation-triangle text-danger fs-1"></i>
                <h4 class="mt-3">Erro ao carregar chip</h4>
                <p class="text-muted">${error.message}</p>

                <button class="btn btn-primary mt-3"
                        onclick="window.location.href='chips.html'">
                    Voltar
                </button>
            </div>
        `;

        showToast(error.message, "danger");
    }
}

function editarChip() {
    window.location.href = `chip-edit.html?id=${id}`;
}

window.editarChip = editarChip;

carregar();
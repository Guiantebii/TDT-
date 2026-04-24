import { apiRequest } from "./api.js";

let chipIdParaDeletar = null;

function showToast(message, type = "success") {
    const toastEl = document.getElementById("toast");
    const messageEl = document.getElementById("toast-message");

    messageEl.textContent = message;
    toastEl.className = `toast align-items-center text-white bg-${type} border-0`;

    new bootstrap.Toast(toastEl).show();
}

async function carregarChips() {
    const tabela = document.getElementById("tabela-chips");

    tabela.innerHTML = `
        <tr>
            <td colspan="3">Carregando...</td>
        </tr>
    `;

    try {
        const chips = await apiRequest("/chips");

        if (chips.length === 0) {
            tabela.innerHTML = `
                <tr>
                    <td colspan="3">Nenhum chip encontrado</td>
                </tr>
            `;
            return;
        }

        tabela.innerHTML = chips.map(chip => `
            <tr>
                <td>${chip.iccid}</td>
                <td>${chip.status}</td>
                <td>
                    <i class="bi bi-eye text-primary mx-1"
                       style="cursor:pointer"
                       onclick="visualizar(${chip.id})"></i>

                    <i class="bi bi-pencil text-warning mx-1"
                       style="cursor:pointer"
                       onclick="editar(${chip.id})"></i>

                    <i class="bi bi-trash text-danger mx-1"
                       style="cursor:pointer"
                       onclick="deletar(${chip.id})"></i>
                </td>
            </tr>
        `).join("");

    } catch (error) {
        tabela.innerHTML = `
            <tr>
                <td colspan="3">Erro ao carregar dados</td>
            </tr>
        `;
        showToast(error.message, "danger");
    }
}

function visualizar(id) {
    window.location.href = `chip-view.html?id=${id}`;
}

function editar(id) {
    window.location.href = `chip-edit.html?id=${id}`;
}

function deletar(id) {
    chipIdParaDeletar = id;

    const modal = new bootstrap.Modal(document.getElementById("modalDelete"));
    modal.show();
}

document.getElementById("btnConfirmDelete").addEventListener("click", async () => {

    try {
        await apiRequest(`/chips/${chipIdParaDeletar}`, {
            method: "DELETE"
        });

        showToast("Chip deletado com sucesso", "success");
        carregarChips();

    } catch (error) {
        showToast(error.message, "danger");
    }

    const modal = bootstrap.Modal.getInstance(document.getElementById("modalDelete"));
    modal.hide();
});

window.visualizar = visualizar;
window.editar = editar;
window.deletar = deletar;

window.onload = carregarChips;
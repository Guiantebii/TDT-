import { apiRequest } from "./api.js";

class ManutencaoManager {
    constructor() {
        this.tabela = document.getElementById("tabela-manutencoes");
        this.init();
    }

    init() {
        this.carregarManutencoes();
        this.setupEventListeners();
    }

    setupEventListeners() {
        this.tabela.addEventListener('click', (e) => {
            const btn = e.target.closest("i");
            if (!btn) return;

            const id = btn.dataset.id;
            if (!id) return;

            if (btn.classList.contains('btn-edit')) {
                this.editar(id);
            }

            if (btn.classList.contains('btn-delete')) {
                this.deletar(id);
            }
        });

        document.getElementById("btnConfirmDelete")
            .addEventListener("click", () => this.confirmarExclusao());
    }

    async carregarManutencoes() {
        this.tabela.innerHTML = `<tr><td colspan="4">Carregando...</td></tr>`;

        try {
            const manutencoes = await apiRequest("/manutencoes");

            if (manutencoes.length === 0) {
                this.tabela.innerHTML = `<tr><td colspan="4" class="text-muted">Nenhuma manutenção encontrada.</td></tr>`;
                return;
            }

            this.tabela.innerHTML = manutencoes.map(m => `
                <tr>
                    <td>${m.id}</td>
                    <td>${m.tc_id}</td>
                    <td class="text-start">${m.descricao || '-'}</td>
                    <td>
                        <i class="bi bi-pencil text-warning mx-1 btn-edit"
                           data-id="${m.id}" style="cursor:pointer" title="Editar"></i>

                        <i class="bi bi-trash text-danger mx-1 btn-delete"
                           data-id="${m.id}" style="cursor:pointer" title="Excluir"></i>
                    </td>
                </tr>
            `).join("");

        } catch (error) {
            this.tabela.innerHTML = `<tr><td colspan="4">Erro ao carregar</td></tr>`;
            this.showToast("Erro ao carregar manutenções", "danger");
        }
    }

    deletar(id) {
        this.manutencaoDeleteId = id;
        new bootstrap.Modal(document.getElementById("modalDelete")).show();
    }

    async confirmarExclusao() {
        try {
            await apiRequest(`/manutencoes/${this.manutencaoDeleteId}`, {
                method: "DELETE"
            });

            this.showToast("Manutenção deletada com sucesso", "success");
            this.carregarManutencoes();

        } catch (error) {
            this.showToast(error.message, "danger");
        }

        bootstrap.Modal.getInstance(document.getElementById("modalDelete")).hide();
    }

    editar(id) {
        window.location.href = `manutencao-edit.html?id=${id}`;
    }

    showToast(msg, type = "success") {
        const el = document.getElementById("toast");
        el.className = `toast show bg-${type}`;
        el.textContent = msg;

        setTimeout(() => {
            el.classList.remove("show");
        }, 3000);
    }
}

document.addEventListener("DOMContentLoaded", () => new ManutencaoManager());
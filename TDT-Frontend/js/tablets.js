import { apiRequest } from "./api.js";

class TabletManager {
    constructor() {
        this.tabela = document.getElementById("tabela-tablets");
        this.busca = document.getElementById("busca");
        this.init();
    }

    init() {
        this.carregarTablets();
        this.setupEventListeners();
    }

    setupEventListeners() {
        this.tabela.addEventListener('click', (e) => {
            const id = e.target.dataset.id;
            
            if (e.target.classList.contains('btn-edit')) {
                this.editar(id);
            }
            
            if (e.target.classList.contains('btn-delete')) {
                this.deletar(id);
            }
        });


        this.busca.addEventListener('input', this.debounce(() => {
            this.filtrarTablets(this.busca.value);
        }, 300));

        document.getElementById("btnConfirmDelete")
            .addEventListener("click", () => this.confirmarExclusao());
    }

    debounce(func, wait) {
        let timeout;
        return (...args) => {
            clearTimeout(timeout);
            timeout = setTimeout(() => func.apply(this, args), wait);
        };
    }

    async carregarTablets() {
        this.renderLoading();

        try {
            const tablets = await apiRequest("/tablets");
            this.tablets = tablets; // Guardar para filtro
            this.renderTablets(tablets);
        } catch (error) {
            this.renderError();
            this.showToast(error.message, "danger");
        }
    }

    renderTablets(tablets) {
        if (tablets.length === 0) {
            this.tabela.innerHTML = `
                <tr><td colspan="3">Nenhum tablet encontrado</td></tr>
            `;
            return;
        }

        this.tabela.innerHTML = tablets.map(tablet => `
            <tr>
                <td>${this.escapeHtml(tablet.imei)}</td>
                <td>${this.escapeHtml(tablet.ns)}</td>
                <td>
                    <i class="bi bi-pencil text-warning mx-1 btn-edit"
                       style="cursor:pointer"
                       data-id="${tablet.id}"></i>

                    <i class="bi bi-trash text-danger mx-1 btn-delete"
                       style="cursor:pointer"
                       data-id="${tablet.id}"></i>
                </td>
            </tr>
        `).join("");
    }

    renderLoading() {
        this.tabela.innerHTML = `<tr><td colspan="3">Carregando...</td></tr>`;
    }

    renderError() {
        this.tabela.innerHTML = `<tr><td colspan="3">Erro ao carregar dados</td></tr>`;
    }

    filtrarTablets(termo) {
        const filtrados = this.tablets.filter(t => 
            t.imei.toLowerCase().includes(termo.toLowerCase())
        );
        this.renderTablets(filtrados);
    }

    escapeHtml(text) {
        const div = document.createElement('div');
        div.textContent = text;
        return div.innerHTML;
    }

    editar(id) {
        window.location.href = `tablet-edit.html?id=${id}`;
    }

    deletar(id) {
        this.tabletIdParaDeletar = id;
        const modal = new bootstrap.Modal(document.getElementById("modalDelete"));
        modal.show();
    }

    async confirmarExclusao() {
        try {
            await apiRequest(`/tablets/${this.tabletIdParaDeletar}`, {
                method: "DELETE"
            });

            this.showToast("Tablet deletado com sucesso", "success");
            this.carregarTablets();
        } catch (error) {
            this.showToast(error.message, "danger");
        }

        bootstrap.Modal.getInstance(document.getElementById("modalDelete")).hide();
    }

    showToast(message, type = "success") {
        const toastEl = document.getElementById("toast");
        const messageEl = document.getElementById("toast-message");

        messageEl.textContent = message;
        toastEl.className = `toast align-items-center text-white bg-${type} border-0`;

        new bootstrap.Toast(toastEl).show();
    }
}

document.addEventListener('DOMContentLoaded', () => {
    new TabletManager();
});
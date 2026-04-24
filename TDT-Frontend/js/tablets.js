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

    const params = new URLSearchParams(window.location.search);
    const tabletId = params.get("vincular");

    if (tabletId) {
        this.abrirModalChip(tabletId);
    }
}

    setupEventListeners() {

        this.tabela.addEventListener('click', (e) => {

            const btn = e.target.closest("i"); // 🔥 CORREÇÃO PRINCIPAL

            if (!btn) return;

            const id = btn.dataset.id;

            if (!id) return;

            if (btn.classList.contains('btn-edit')) {
                this.editar(id);
            }

            if (btn.classList.contains('btn-delete')) {
                this.deletar(id);
            }

            if (btn.classList.contains('btn-chip')) {
                this.abrirModalChip(id);
            }
        });

        document.getElementById("btnConfirmDelete")
            .addEventListener("click", () => this.confirmarExclusao());

        document.getElementById("btnVincularChip")
            .addEventListener("click", () => this.vincularChip());
    }

    async carregarTablets() {
        this.tabela.innerHTML = `<tr><td colspan="5">Carregando...</td></tr>`;

        try {
            const tablets = await apiRequest("/tablets");

            this.tabela.innerHTML = tablets.map(t => `
                <tr>
                    <td>${t.imei}</td>
                    <td>${t.ns}</td>
                    <td>${t.chipIccid ?? "-"}</td>
                    <td>${t.chipStatus ?? "-"}</td>
                    <td>
                        <i class="bi bi-sim text-success mx-1 btn-chip"
                           data-id="${t.id}" style="cursor:pointer"></i>

                        <i class="bi bi-pencil text-warning mx-1 btn-edit"
                           data-id="${t.id}" style="cursor:pointer"></i>

                        <i class="bi bi-trash text-danger mx-1 btn-delete"
                           data-id="${t.id}" style="cursor:pointer"></i>
                    </td>
                </tr>
            `).join("");

        } catch (error) {
            this.tabela.innerHTML = `<tr><td colspan="5">Erro ao carregar</td></tr>`;
            this.showToast("Erro ao carregar tablets", "danger");
        }
    }

    async abrirModalChip(id) {
        this.tabletSelecionado = id;

        const select = document.getElementById("selectChip");
        select.innerHTML = `<option>Carregando...</option>`;

        try {
            const chips = await apiRequest("/chips");

            if (chips.length === 0) {
                select.innerHTML = `<option>Nenhum chip disponível</option>`;
                return;
            }

            select.innerHTML = chips.map(c =>
                `<option value="${c.id}">${c.iccid} - ${c.status}</option>`
            ).join("");

            new bootstrap.Modal(document.getElementById("modalChip")).show();

        } catch (error) {
            this.showToast("Erro ao carregar chips", "danger");
        }
    }

    async vincularChip() {
        const chipId = document.getElementById("selectChip").value;

        if (!chipId) {
            this.showToast("Selecione um chip", "danger");
            return;
        }

        try {
            await apiRequest(`/tablets/${this.tabletSelecionado}/vincular-chip`, {
                method: "POST",
                body: JSON.stringify({ chipId: parseInt(chipId) })
            });

            this.showToast("Chip vinculado com sucesso!", "success");

            bootstrap.Modal.getInstance(
                document.getElementById("modalChip")
            ).hide();

            this.carregarTablets();

        } catch (error) {
            this.showToast(error.message || "Erro ao vincular chip", "danger");
        }
    }

    deletar(id) {
        this.tabletDelete = id;
        new bootstrap.Modal(document.getElementById("modalDelete")).show();
    }

    async confirmarExclusao() {
        try {
            await apiRequest(`/tablets/${this.tabletDelete}`, {
                method: "DELETE"
            });

            this.showToast("Tablet deletado com sucesso", "success");
            this.carregarTablets();

        } catch (error) {
            this.showToast(error.message, "danger");
        }

        bootstrap.Modal.getInstance(
            document.getElementById("modalDelete")
        ).hide();
    }

    editar(id) {
        window.location.href = `tablet-edit.html?id=${id}`;
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

document.addEventListener("DOMContentLoaded", () => new TabletManager());
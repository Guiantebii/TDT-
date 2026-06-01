import { apiRequest } from "./api.js";

class TabletManager {

    constructor() {
        this.tabela = document.getElementById("tabela-tablets");
        this.busca = document.getElementById("busca");
        this.todosTablets = []; 
        this.todosChips = [];
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

            if (btn.classList.contains('btn-chip')) {
                this.abrirModalChip(id);
            }
        });

        document.getElementById("btnConfirmDelete")
            .addEventListener("click", () => this.confirmarExclusao());

        document.getElementById("btnVincularChip")
            .addEventListener("click", () => this.vincularChip());

        this.busca.addEventListener("input", (e) => this.filtrarTablets(e.target.value));

     
        document.getElementById("buscaChip").addEventListener("input", (e) => {
            const termoBusca = e.target.value.toLowerCase().trim();
            
            const filtrados = this.todosChips.filter(c => 
                c.iccid && c.iccid.toLowerCase().includes(termoBusca)
            );

            this.renderizarChips(filtrados);
        });
    }

    async carregarTablets() {
        this.tabela.innerHTML = `<tr><td colspan="5">Carregando...</td></tr>`;

        try {
            this.todosTablets = await apiRequest("/tablets");
            this.renderizarTabela(this.todosTablets);

        } catch (error) {
            this.tabela.innerHTML = `<tr><td colspan="5">Erro ao carregar</td></tr>`;
            this.showToast("Erro ao carregar tablets", "danger");
        }
    }

    renderizarTabela(lista) {
        if (lista.length === 0) {
            this.tabela.innerHTML = `<tr><td colspan="5" class="text-muted">Nenhum tablet encontrado.</td></tr>`;
            return;
        }

        this.tabela.innerHTML = lista.map(t => `
            <tr>
                <td>${t.imei}</td>
                <td>${t.ns}</td>
                <td>${t.chipIccid ?? "-"}</td>
                <td>${t.chipStatus ?? "-"}</td>
                <td>
                    <i class="bi bi-sim text-success mx-1 btn-chip"
                       data-id="${t.id}" style="cursor:pointer" title="Vincular Chip"></i>

                    <i class="bi bi-pencil text-warning mx-1 btn-edit"
                       data-id="${t.id}" style="cursor:pointer" title="Editar"></i>

                    <i class="bi bi-trash text-danger mx-1 btn-delete"
                       data-id="${t.id}" style="cursor:pointer" title="Excluir"></i>
                </td>
            </tr>
        `).join("");
    }

    filtrarTablets(termo) {
        const termoBusca = termo.toLowerCase().trim();
        
        const filtrados = this.todosTablets.filter(t => 
            (t.imei && t.imei.toLowerCase().includes(termoBusca)) || 
            (t.ns && t.ns.toLowerCase().includes(termoBusca))
        );

        this.renderizarTabela(filtrados);
    }

    renderizarChips(lista) {
        const select = document.getElementById("selectChip");

        if (lista.length === 0) {
            select.innerHTML = `<option value="" disabled selected>Nenhum chip encontrado...</option>`;
            return;
        }

        select.innerHTML = lista.map(c =>
            `<option value="${c.id}">${c.iccid} - ${c.status}</option>`
        ).join("");
    }

    async abrirModalChip(id) {
        this.tabletSelecionado = id;

        const select = document.getElementById("selectChip");
        const inputBusca = document.getElementById("buscaChip");

        select.innerHTML = `<option>Carregando...</option>`;
        inputBusca.value = ""; 

        try {
            this.todosChips = await apiRequest("/chips"); 

            if (this.todosChips.length === 0) {
                select.innerHTML = `<option value="" disabled>Nenhum chip disponível</option>`;
                return;
            }

            this.renderizarChips(this.todosChips); 

            new bootstrap.Modal(document.getElementById("modalChip")).show();

        } catch (error) {
            this.showToast("Erro ao carregar chips", "danger");
        }
    }

    async vincularChip() {
        const chipId = document.getElementById("selectChip").value;

        if (!chipId) {
            this.showToast("Selecione um chip válido", "danger");
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
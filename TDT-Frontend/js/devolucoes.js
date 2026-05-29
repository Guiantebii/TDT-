import { apiRequest } from "./api.js";

class DevolucaoManager {
    constructor() {
        this.tabela = document.getElementById("tabela-devolucoes");
        this.busca = document.getElementById("busca");
        this.todasDevolucoes = [];
        this.init();
    }

    init() {
        this.carregarDevolucoes();
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

        if (this.busca) {
            this.busca.addEventListener("input", (e) => this.filtrarDevolucoes(e.target.value));
        }
    }

    formatarData(dataIso) {
        if (!dataIso) return "-";
        const [ano, mes, dia] = dataIso.split('-');
        return `${dia}/${mes}/${ano}`;
    }

    async carregarDevolucoes() {
        this.tabela.innerHTML = `<tr><td colspan="6">Carregando...</td></tr>`;

        try {
            this.todasDevolucoes = await apiRequest("/devolucoes");
            this.renderizarTabela(this.todasDevolucoes);

        } catch (error) {
            this.tabela.innerHTML = `<tr><td colspan="6">Erro ao carregar</td></tr>`;
            this.showToast("Erro ao carregar devoluções", "danger");
        }
    }

    renderizarTabela(lista) {
        if (lista.length === 0) {
            this.tabela.innerHTML = `<tr><td colspan="6" class="text-muted">Nenhuma devolução encontrada.</td></tr>`;
            return;
        }

        this.tabela.innerHTML = lista.map(d => `
            <tr>
                <td>${d.dvl_id}</td>
                <td>${d.tab_id}</td>
                <td>${d.alu_id}</td>
                <td>${this.formatarData(d.dataEntrega)}</td>
                <td>${this.formatarData(d.dataDevolucao)}</td>
                <td>
                    <i class="bi bi-pencil text-warning mx-1 btn-edit"
                       data-id="${d.dvl_id}" style="cursor:pointer" title="Editar"></i>

                    <i class="bi bi-trash text-danger mx-1 btn-delete"
                       data-id="${d.dvl_id}" style="cursor:pointer" title="Excluir"></i>
                </td>
            </tr>
        `).join("");
    }

    filtrarDevolucoes(termo) {
        const termoBusca = termo.toLowerCase().trim();
        
        const filtrados = this.todasDevolucoes.filter(d => 
            (d.dvl_id && String(d.dvl_id).includes(termoBusca)) || 
            (d.tab_id && String(d.tab_id).includes(termoBusca)) ||
            (d.alu_id && String(d.alu_id).includes(termoBusca))
        );

        this.renderizarTabela(filtrados);
    }

    deletar(id) {
        this.devolucaoDeleteId = id;
        new bootstrap.Modal(document.getElementById("modalDelete")).show();
    }

    async confirmarExclusao() {
        try {
            await apiRequest(`/devolucoes/${this.devolucaoDeleteId}`, {
                method: "DELETE"
            });

            this.showToast("Devolução deletada com sucesso", "success");
            this.carregarDevolucoes();

        } catch (error) {
            this.showToast(error.message, "danger");
        }

        bootstrap.Modal.getInstance(document.getElementById("modalDelete")).hide();
    }

    editar(id) {
        window.location.href = `devolucao-edit.html?id=${id}`;
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

document.addEventListener("DOMContentLoaded", () => new DevolucaoManager());
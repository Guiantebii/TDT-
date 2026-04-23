const API = "http://localhost:8080/tablets";

async function carregarTablets() {
    const tabela = document.getElementById("tabela-tablets");

    try {
        tabela.innerHTML = `
            <tr>
                <td colspan="3">Carregando...</td>
            </tr>
        `;

        const res = await fetch(API);

        if (!res.ok) {
            throw new Error("Erro ao carregar tablets");
        }

        const tablets = await res.json();

        if (tablets.length === 0) {
            tabela.innerHTML = `
                <tr>
                    <td colspan="3">Nenhum tablet encontrado</td>
                </tr>
            `;
            return;
        }

        tabela.innerHTML = "";

        tablets.forEach(tablet => {
            tabela.innerHTML += `
                <tr>
                    <td>${tablet.imei}</td>
                    <td>${tablet.ns}</td>
                    <td>
                        <i class="bi bi-eye text-primary mx-1"
                           style="cursor:pointer"
                           onclick="visualizar(${tablet.id})"></i>

                        <i class="bi bi-pencil text-warning mx-1"
                           style="cursor:pointer"
                           onclick="editar(${tablet.id})"></i>

                        <i class="bi bi-trash text-danger mx-1"
                           style="cursor:pointer"
                           onclick="deletar(${tablet.id})"></i>
                    </td>
                </tr>
            `;
        });

    } catch (error) {
        tabela.innerHTML = `
            <tr>
                <td colspan="3">Erro ao carregar dados</td>
            </tr>
        `;
        alert(error.message);
    }
}

function visualizar(id) {
    window.location.href = `tablet-view.html?id=${id}`;
}

function editar(id) {
    window.location.href = `tablet-edit.html?id=${id}`;
}

async function deletar(id) {
    const confirmar = confirm("Deseja realmente excluir este tablet?");
    if (!confirmar) return;

    try {
        const res = await fetch(`${API}/${id}`, {
            method: "DELETE"
        });

        if (!res.ok) {
            throw new Error("Erro ao deletar tablet");
        }

        carregarTablets();

    } catch (error) {
        alert(error.message);
    }
}

window.onload = carregarTablets;
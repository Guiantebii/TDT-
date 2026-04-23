const API = "http://localhost:8080/alunos";


async function carregarAlunos() {
    const tabela = document.getElementById("tabela-alunos");

    try {
        tabela.innerHTML = `
            <tr>
                <td colspan="4">Carregando...</td>
            </tr>
        `;

        const res = await fetch(API);

        if (!res.ok) {
            throw new Error("Erro ao carregar alunos");
        }

        const alunos = await res.json();

        if (alunos.length === 0) {
            tabela.innerHTML = `
                <tr>
                    <td colspan="4">Nenhum aluno encontrado</td>
                </tr>
            `;
            return;
        }

        tabela.innerHTML = "";

        alunos.forEach(aluno => {
            tabela.innerHTML += `
                <tr>
                    <td>${aluno.nome}</td>
                    <td>${aluno.eol}</td>
                    <td>${aluno.turma}</td>
                    <td>
                        <i class="bi bi-eye text-primary mx-1"
                           style="cursor:pointer"
                           onclick="visualizar(${aluno.id})"></i>

                        <i class="bi bi-pencil text-warning mx-1"
                           style="cursor:pointer"
                           onclick="editar(${aluno.id})"></i>

                        <i class="bi bi-trash text-danger mx-1"
                           style="cursor:pointer"
                           onclick="deletar(${aluno.id})"></i>
                    </td>
                </tr>
            `;
        });

    } catch (error) {
        tabela.innerHTML = `
            <tr>
                <td colspan="4">Erro ao carregar dados</td>
            </tr>
        `;
        alert(error.message);
    }
}

function visualizar(id) {
    window.location.href = `aluno-view.html?id=${id}`;
}

function editar(id) {
    window.location.href = `aluno-edit.html?id=${id}`;
}

async function deletar(id) {
    const confirmar = confirm("Deseja realmente excluir este aluno?");
    if (!confirmar) return;

    try {
        const res = await fetch(`${API}/${id}`, {
            method: "DELETE"
        });

        if (!res.ok) {
            throw new Error("Erro ao deletar aluno");
        }

        carregarAlunos();

    } catch (error) {
        alert(error.message);
    }
}

window.onload = carregarAlunos;
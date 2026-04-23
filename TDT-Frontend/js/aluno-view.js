const API = "http://localhost:8080/alunos";

const params = new URLSearchParams(window.location.search);
const id = params.get("id");

async function carregar() {
    try {
        const res = await fetch(`${API}/${id}`);

        if (!res.ok) throw new Error("Aluno não encontrado");

        const aluno = await res.json();

        document.getElementById("nome").textContent = aluno.nome;
        document.getElementById("eol").textContent = aluno.eol;
        document.getElementById("turma").textContent = aluno.turma;
        document.getElementById("tel1").textContent = aluno.tel1;
        document.getElementById("tel2").textContent = aluno.tel2;

        const data = new Date(aluno.dataNasc);
        document.getElementById("dataNasc").textContent =
            data.toLocaleDateString("pt-BR");

    } catch (error) {
        alert(error.message);
    }
}

function editarAluno() {
    window.location.href = `aluno-edit.html?id=${id}`;
}

carregar();
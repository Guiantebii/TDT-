const API = "http://localhost:8080/alunos";

const params = new URLSearchParams(window.location.search);
const id = params.get("id");


async function carregarAluno() {
    try {
        const res = await fetch(`${API}/${id}`);

        if (!res.ok) throw new Error("Aluno não encontrado");

        const aluno = await res.json();

        document.getElementById("nome").value = aluno.nome;
        document.getElementById("eol").value = aluno.eol;
        document.getElementById("turma").value = aluno.turma;
        document.getElementById("tel1").value = aluno.tel1;
        document.getElementById("tel2").value = aluno.tel2;
        document.getElementById("dataNasc").value = aluno.dataNasc;

    } catch (error) {
        alert(error.message);
    }
}

async function salvar() {
    try {
        const aluno = {
            nome: document.getElementById("nome").value,
            eol: document.getElementById("eol").value,
            turma: document.getElementById("turma").value,
            tel1: document.getElementById("tel1").value,
            tel2: document.getElementById("tel2").value,
            dataNasc: document.getElementById("dataNasc").value
        };

        const res = await fetch(`${API}/${id}`, {
            method: "PUT",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(aluno)
        });

        if (!res.ok) throw new Error("Erro ao atualizar aluno");

        alert("Aluno atualizado com sucesso!");
        window.location.href = "alunos.html";

    } catch (error) {
        alert(error.message);
    }
}


window.onload = carregarAluno;
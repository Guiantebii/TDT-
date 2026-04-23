const API = "http://localhost:8080/alunos";

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

        const res = await fetch(API, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(aluno)
        });

        if (!res.ok) {
            throw new Error("Erro ao criar aluno");
        }

        alert("Aluno criado com sucesso!");
        window.location.href = "alunos.html";

    } catch (error) {
        alert("Erro: " + error.message);
    }
}
const API = "http://localhost:8080/tablets";

async function salvar() {
    try {
        const tablet = {
            imei: document.getElementById("imei").value,
            ns: document.getElementById("ns").value
        };

        const res = await fetch(API, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(tablet)
        });

        if (!res.ok) {
            throw new Error("Erro ao criar tablet");
        }

        alert("Tablet criado com sucesso!");
        window.location.href = "tablets.html";

    } catch (error) {
        alert(error.message);
    }
}
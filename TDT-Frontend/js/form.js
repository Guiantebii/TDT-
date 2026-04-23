export function limparErros() {
    document.querySelectorAll(".is-invalid").forEach(el => {
        el.classList.remove("is-invalid");
    });

    document.querySelectorAll(".invalid-feedback").forEach(el => {
        el.remove();
    });
}

export function mostrarErrosCampo(erros) {
    Object.keys(erros).forEach(campo => {
        const input = document.getElementById(campo);

        if (input) {
            input.classList.add("is-invalid");

            const div = document.createElement("div");
            div.className = "invalid-feedback";
            div.innerText = erros[campo];

            input.parentNode.appendChild(div);
        }
    });
}
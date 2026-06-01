const BASE_URL = "http://localhost:8080";

export async function apiRequest(endpoint, options = {}) {
    const res = await fetch(`${BASE_URL}${endpoint}`, {
        headers: {
            "Content-Type": "application/json",
            ...options.headers
        },
        ...options
    });


    if (!res.ok) {
        let error;
        try {
            error = await res.json();
        } catch (e) {
     
            throw new Error(`Erro no servidor (Status: ${res.status}).`);
        }

   
        if (!error.message) {
            throw { validation: error };
        }

        throw new Error(error.message || "Erro na requisição");
    }

    if (res.status === 204) {
        return null;
    }

    return res.json();
}
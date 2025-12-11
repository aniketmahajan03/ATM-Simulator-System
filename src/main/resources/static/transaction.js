// Local URL
// const BASE_URL = "/api/accounts";

// Global URL 
const BASE_URL = "https://atm-simulator-system.onrender.com/api/accounts";

async function deposit() {
    const acc = document.getElementById("accountId").value;
    const amt = parseFloat(document.getElementById("amount").value);

    const res = await fetch(`${BASE_URL}/${acc}/deposit`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ amount: amt })
    });

    alert(await res.text());
}

async function withdraw() {
    const acc = document.getElementById("accountId").value;
    const amt = parseFloat(document.getElementById("amount").value);

    const res = await fetch(`${BASE_URL}/${acc}/withdraw`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ amount: amt })
    });

    alert(await res.text());
}

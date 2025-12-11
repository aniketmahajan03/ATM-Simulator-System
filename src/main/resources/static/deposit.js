// Local URL
// const BASE = "/api/accounts";

// Global URL
const BASE_URL = "https://atm-simulator-system.onrender.com/api/accounts";

document.addEventListener("DOMContentLoaded", () => {
  const form = document.getElementById("depositForm");
  const out = document.getElementById("depositOutput");

  form.addEventListener("submit", async (e) => {
    e.preventDefault();
    out.style.display = "none";

    const card = document.getElementById("depositCard").value.trim();
    const amount = parseFloat(document.getElementById("depositAmount").value);

    if (!card || isNaN(amount) || amount <= 0) {
      out.style.display = "block";
      out.innerText = "Enter valid card and amount.";
      return;
    }

    try {
      const res = await fetch(`${BASE}/${encodeURIComponent(card)}/deposit`, {
        method: "POST",
        headers: {"Content-Type":"application/json"},
        body: JSON.stringify({ amount })
      });

      if (!res.ok) {
        const text = await res.text();
        throw new Error(text || ("Status " + res.status));
      }

      const data = await res.json();
      out.style.display = "block";
      out.innerText = `✅ ${data.message} · New Balance: ₹${data.balance}`;
      form.reset();
    } catch (err) {
      out.style.display = "block";
      out.innerText = "Error: " + err.message;
      console.error(err);
    }
  });
});

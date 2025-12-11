// Local URL
// const BASE = "/api/accounts";

// Global URL
const BASE = "https://atm-simulator-system.onrender.com/api/accounts";

document.addEventListener("DOMContentLoaded", () => {
  const form = document.getElementById("withdrawForm");
  const out = document.getElementById("withdrawOutput");

  form.addEventListener("submit", async (e) => {
    e.preventDefault();
    out.style.display = "none";

    const card = document.getElementById("withdrawCard").value.trim();
    const amount = parseFloat(document.getElementById("withdrawAmount").value);

    if (!card || isNaN(amount) || amount <= 0) {
      out.style.display = "block";
      out.innerText = "Enter valid card and amount.";
      return;
    }

    try {
      const res = await fetch(`${BASE}/${encodeURIComponent(card)}/withdraw`, {
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

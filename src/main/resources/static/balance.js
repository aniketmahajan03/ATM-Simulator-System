// Local URL
// const BASE = "/api/accounts";

// Global URL
const BASE_URL = "https://atm-simulator-system.onrender.com/api/accounts";

document.addEventListener("DOMContentLoaded", () => {
  const form = document.getElementById("balanceForm");
  const out = document.getElementById("balanceOutput");

  form.addEventListener("submit", async (e) => {
    e.preventDefault();
    out.style.display = "none";

    const card = document.getElementById("balanceCard").value.trim();
    if (!card) {
      out.style.display = "block";
      out.innerText = "Enter card number.";
      return;
    }

    try {
      const res = await fetch(`${BASE}/balance/${encodeURIComponent(card)}`);
      if (!res.ok) {
        const text = await res.text();
        throw new Error(text || ("Status " + res.status));
      }
      const data = await res.json ? await res.json() : await res.text();
      out.style.display = "block";
      // backend returns BigDecimal for balance — convert to string
      out.innerText = `Available Balance: ₹${data}`;
    } catch (err) {
      out.style.display = "block";
      out.innerText = "Error: " + err.message;
      console.error(err);
    }
  });
});

// Local URL
// const BASE = "/api/accounts";

// Global URL
const BASE = "https://atm-simulator-system.onrender.com/api/accounts";

document.addEventListener("DOMContentLoaded", () => {
  const form = document.getElementById("createForm");
  const out = document.getElementById("createOutput");

  form.addEventListener("submit", async (e) => {
    e.preventDefault();
    out.style.display = "none";
    const holderName = document.getElementById("holderName").value.trim();
    const accountNumber = document.getElementById("cardNumber").value.trim();
    const initialBalance = parseFloat(document.getElementById("initialBalance").value);

    if (!holderName || !accountNumber || isNaN(initialBalance)) {
      out.style.display = "block";
      out.innerText = "Please provide valid name, card number and initial balance.";
      return;
    }

    try {
      const res = await fetch(BASE, {
        method: "POST",
        headers: {"Content-Type":"application/json"},
        body: JSON.stringify({ holderName, accountNumber, initialBalance })
      });

      if (!res.ok) throw new Error("Server error " + res.status);
      const data = await res.json();
      out.style.display = "block";
      out.innerText = `✅ Account created. ID: ${data.id} · Card: ${data.accountNumber}`;
      form.reset();
    } catch (err) {
      out.style.display = "block";
      out.innerText = "Error creating account: " + err.message;
      console.error(err);
    }
  });
});

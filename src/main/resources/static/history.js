// Local URL
// const BASE = "/api/accounts";

// Global URL 
const BASE = "https://atm-simulator-system.onrender.com/api/accounts";

document.addEventListener("DOMContentLoaded", () => {
  const form = document.getElementById("historyForm");
  const out = document.getElementById("historyOutput");
  const table = document.getElementById("historyTable");
  const tbody = table.querySelector("tbody");
  const downloadBtn = document.getElementById("downloadBtn");

  form.addEventListener("submit", async (e) => {
    e.preventDefault();
    out.style.display = "none";
    table.style.display = "none";
    tbody.innerHTML = "";

    const card = document.getElementById("historyCard").value.trim();
    if (!card) { out.style.display = "block"; out.innerText = "Enter card number."; return; }

    try {
      const res = await fetch(`${BASE}/${encodeURIComponent(card)}/transactions`);
      if (!res.ok) { throw new Error("Status " + res.status); }
      const data = await res.json();

      if (!Array.isArray(data) || data.length === 0) {
        out.style.display = "block";
        out.innerText = "No transactions found.";
        return;
      }

      table.style.display = "table";
      data.forEach(tx => {
        const tr = document.createElement("tr");
        tr.innerHTML = `<td>${tx.id || ""}</td>
                        <td>${tx.type || ""}</td>
                        <td>₹${tx.amount || ""}</td>
                        <td>${tx.timestamp || ""}</td>
                        <td>${tx.description || ""}</td>`;
        tbody.appendChild(tr);
      });

    } catch (err) {
      out.style.display = "block";
      out.innerText = "Error: " + err.message;
      console.error(err);
    }
  });

  downloadBtn.addEventListener("click", () => {
    const card = document.getElementById("historyCard").value.trim();
    if (!card) { out.style.display = "block"; out.innerText = "Enter card number first."; return; }
    window.open(`${BASE}/${encodeURIComponent(card)}/mini/download`, "_blank");
  });
});

console.log("SCRIPT JS IS LOADED");

// For testing Locally
// const BASE_URL = "http://localhost:8080/api/accounts";

// For Uploading on Render,GitHub -
const BASE_URL = "https://atm-simulator-system.onrender.com/api/accounts";

async function createAccount() {
  const holderName = document.getElementById("holderName").value;
  const accountNumber = document.getElementById("accountNumber").value;
  const initialBalance = parseFloat(document.getElementById("initialBalance").value);

  const res = await fetch(BASE_URL, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ holderName, accountNumber, initialBalance })
  });
  const data = await res.json();
  alert("✅ Account created successfully! ID: " + data.id);
}

async function deposit() {
  const id = document.getElementById("accountId").value;
  const amount = parseFloat(document.getElementById("amount").value);

  const res = await fetch(`${BASE_URL}/${id}/deposit`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ amount })
  });
  const data = await res.json();
  alert("💰 New Balance: ₹" + data.balance);
}

async function withdraw() {
  const id = document.getElementById("accountId").value;
  const amount = parseFloat(document.getElementById("amount").value);

  const res = await fetch(`${BASE_URL}/${id}/withdraw`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ amount })
  });
  const data = await res.json();
  alert("💸 New Balance: ₹" + data.balance);
}

async function checkBalance() {
  const accountNumber = document.getElementById("checkAccountNumber").value;
  const res = await fetch(`${BASE_URL}/balance/${accountNumber}`);
  const data = await res.text();
  document.getElementById("balanceResult").innerText = data;
}
async function showHistory() {
    const id = document.getElementById("historyAccountId").value;
    console.log("📌 Account ID entered:", id);

    if (!id || id.trim() === "") {
        alert("Please enter account ID");
        return;
    }

    try {
        const url = `${BASE_URL}/${id}/transactions`;  // ✅ Correct URL
        console.log("🌐 Fetching URL:", url);

        const response = await fetch(url);

        console.log("🔍 Response status:", response.status);

        const data = await response.json();
        console.log("📦 Parsed Data:", data);

        const tableBody = document.querySelector("#historyTable tbody");
        tableBody.innerHTML = "";

        if (!Array.isArray(data) || data.length === 0) {
            tableBody.innerHTML = "<tr><td colspan='5'>No transactions found</td></tr>";
            return;
        }

        data.forEach(txn => {
            const row = `
                <tr>
                    <td>${txn.id}</td>
                    <td>${txn.type}</td>
                    <td>₹${txn.amount}</td>
                    <td>${txn.timestamp}</td>
                    <td>${txn.description}</td>
                </tr>
            `;
            tableBody.innerHTML += row;
        });

    } catch (error) {
        console.error("❌ ERROR:", error);
        alert("Something went wrong. Check console.");
        

    }

}







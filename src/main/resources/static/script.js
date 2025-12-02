// For testing Locally
// const BASE_URL = "http://localhost:8080/api/accounts";

// Base URL  
const BASE_URL = "https://atm-simulator-system.onrender.com/api/accounts";
// ✅ CREATE ACCOUNT
async function createAccount() {
  const holderName = document.getElementById("holderName").value;
  const accountNumber = document.getElementById("accountNumber").value;
  const initialBalance = parseFloat(document.getElementById("initialBalance").value);

  try {
    const res = await fetch(BASE_URL, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ holderName, accountNumber, initialBalance })
    });

    const data = await res.json();
    alert(`✅ Account created successfully! ID: ${data.id}, Account Number: ${data.accountNumber}`);
  } catch (error) {
    console.error("❌ Error creating account:", error);
  }
}

// ✅ DEPOSIT
async function deposit() {
  const accountNumber = document.getElementById("accountId").value;
  const amount = parseFloat(document.getElementById("amount").value);

  try {
    const res = await fetch(`${BASE_URL}/${accountNumber}/deposit`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ amount })
    });

    const data = await res.json();
    alert(`💰 ${data.message}\nNew Balance: ₹${data.balance}`);
  } catch (error) {
    console.error("❌ Deposit error:", error);
  }
}

// ✅ WITHDRAW
async function withdraw() {
  const accountNumber = document.getElementById("accountId").value;
  const amount = parseFloat(document.getElementById("amount").value);

  try {
    const res = await fetch(`${BASE_URL}/${accountNumber}/withdraw`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ amount })
    });

    const data = await res.json();
    alert(`💸 ${data.message}\nNew Balance: ₹${data.balance}`);
  } catch (error) {
    console.error("❌ Withdraw error:", error);
  }
}

// ✅ CHECK BALANCE
async function checkBalance() {
  const accountNumber = document.getElementById("checkAccountNumber").value;

  try {
    const res = await fetch(`${BASE_URL}/balance/${accountNumber}`);
    const data = await res.text();
    document.getElementById("balanceResult").innerText = data;
  } catch (error) {
    console.error("❌ Check balance error:", error);
  }
}


// ✅ MINI STATEMENT (WORKING)
async function showMiniStatement() {
  const accountNumber = document.getElementById("miniAccountNumber").value;

  if (!accountNumber) {
    alert("Please enter account number");
    return;
  }

  try {
    const res = await fetch(`${BASE_URL}/${accountNumber}/mini`);
    const transactions = await res.json();

    const tableBody = document.querySelector("#miniTable tbody");
    tableBody.innerHTML = "";

    if (!transactions.length) {
      tableBody.innerHTML = "<tr><td colspan='5'>No transactions found</td></tr>";
      return;
    }

    transactions.forEach(txn => {
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
    console.error("❌ Mini statement error:", error);
  }
}

// ✅ DOWNLOAD MINI STATEMENT (WORKING)
function downloadMiniStatement() {
  const accountId = document.getElementById("miniStmtId").value;

  if (!accountId) {
    alert("Enter account ID");
    return;
  }

 
  window.open(`${BASE_URL}/${accountId}/mini/download`, "_blank");
}
  // ✅ FULL TRANSACTION HISTORY
async function showHistory() {
  const accountNumber = document.getElementById("historyAccountId").value;

  if (!accountNumber) {
    alert("Please enter account number");
    return;
  }

  try {
    const res = await fetch(`${BASE_URL}/${accountNumber}/transactions`);

    if (!res.ok) {
      alert("Failed to fetch transaction history");
      return;
    }

    const transactions = await res.json();

    const tableBody = document.querySelector("#historyTable tbody");
    tableBody.innerHTML = "";

    if (!transactions.length) {
      tableBody.innerHTML = "<tr><td colspan='5'>No transactions found</td></tr>";
      return;
    }

    transactions.forEach(txn => {
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
    console.error("History error:", error);
    alert("Error loading transaction history");
  }

}

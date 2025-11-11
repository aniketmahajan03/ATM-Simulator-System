const BASE_URL = "http://localhost:8080/api/accounts";

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

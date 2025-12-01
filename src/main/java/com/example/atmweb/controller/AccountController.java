package com.example.atmweb.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.atmweb.dto.CreateAccountRequest;
import com.example.atmweb.dto.MoneyRequest;
import com.example.atmweb.dto.TransferRequest;
import com.example.atmweb.model.Account;
import com.example.atmweb.model.Transaction;
import com.example.atmweb.service.AccountService;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/accounts")
@CrossOrigin(origins = "*")
public class AccountController {

    private final AccountService service;

    public AccountController(AccountService service) {
        this.service = service;
    }

    // ✅ Create a new account
    @PostMapping
    public ResponseEntity<Account> createAccount(@RequestBody CreateAccountRequest req) {
        Account created = service.createAccount(req.getHolderName(), req.getAccountNumber(), req.getInitialBalance());
        return ResponseEntity.ok(created);
    }

    // ✅ Get account details by ID
    @GetMapping("/{id}")
    public ResponseEntity<Account> getAccount(@PathVariable Long id) {
        Account account = service.getAccount(id);
        return ResponseEntity.ok(account);
    }

    // ✅ Get balance by Account ID
    @GetMapping("/{id}/balance")
    public ResponseEntity<String> getBalance(@PathVariable Long id) {
        return ResponseEntity.ok("💰 Current balance: ₹" + service.getBalance(id));
    }

    // ✅ Deposit money
    @PostMapping("/{id}/deposit")
    public ResponseEntity<Account> deposit(@PathVariable Long id, @RequestBody MoneyRequest req) {
        Account updated = service.deposit(id, req.getAmount());
        return ResponseEntity.ok(updated);
    }

    // ✅ Withdraw money
    @PostMapping("/{id}/withdraw")
    public ResponseEntity<Account> withdraw(@PathVariable Long id, @RequestBody MoneyRequest req) {
        Account updated = service.withdraw(id, req.getAmount());
        return ResponseEntity.ok(updated);
    }

    // ✅ Transfer money between accounts
    @PostMapping("/transfer")
    public ResponseEntity<String> transfer(@RequestBody TransferRequest req) {
        service.transfer(req.getFromAccountId(), req.getToAccountId(), req.getAmount());
        return ResponseEntity.ok("✅ Transfer successful!");
    }

    // ✅ Get transaction history by Account ID
    @GetMapping("/{id}/transactions")
    public ResponseEntity<List<Transaction>> getHistory(@PathVariable Long id) {
        return ResponseEntity.ok(service.getHistory(id));
    }

    // ✅ Check balance using account number
    @GetMapping("/balance/{accountNumber}")
    public ResponseEntity<String> checkBalanceByAccountNumber(@PathVariable String accountNumber) {
        String balanceInfo = service.getBalanceByAccountNumber(accountNumber);
        return ResponseEntity.ok(balanceInfo);
    }
   @GetMapping("/{id}/mini-statement")
public void downloadMiniStatement(@PathVariable Long id, HttpServletResponse response) throws Exception {

    List<Transaction> transactions = service.getLastTransactions(id, 5); // last 5

    response.setContentType("application/pdf");
    response.setHeader("Content-Disposition", "attachment; filename=mini-statement.pdf");

    com.itextpdf.text.Document pdf = new com.itextpdf.text.Document();
    PdfWriter.getInstance(pdf, response.getOutputStream());
    pdf.open();

    pdf.add(new Paragraph("ATM Mini Statement"));
    pdf.add(new Paragraph("Account ID: " + id));
    pdf.add(new Paragraph("Generated On: " + LocalDateTime.now()));
    pdf.add(new Paragraph("--------------------------------------------------"));

    for (Transaction t : transactions) {
        pdf.add(new Paragraph(
            t.getTimestamp() + "  |  " + t.getType() + "  |  ₹" +
            t.getAmount() + "  |  " + t.getDescription()
        ));
    }

    pdf.close();
}


}

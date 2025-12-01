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

    // ✅ CREATE ACCOUNT
    @PostMapping
    public ResponseEntity<Account> createAccount(@RequestBody CreateAccountRequest req) {
        Account created = service.createAccount(
                req.getHolderName(),
                req.getAccountNumber(),
                req.getInitialBalance()
        );
        return ResponseEntity.ok(created);
    }

    // ✅ CHECK BALANCE (BY ACCOUNT NUMBER)
    @GetMapping("/balance/{accountNumber}")
    public ResponseEntity<String> checkBalance(@PathVariable String accountNumber) {
        return ResponseEntity.ok(
                "💰 Current Balance: ₹" + service.checkBalance(accountNumber)
        );
    }

    // ✅ DEPOSIT (BY ACCOUNT NUMBER)
    @PostMapping("/{accountNumber}/deposit")
    public ResponseEntity<String> deposit(
            @PathVariable String accountNumber,
            @RequestBody MoneyRequest req) {

        return ResponseEntity.ok(
                "✅ New Balance: ₹" +
                        service.deposit(accountNumber, req.getAmount())
        );
    }

    // ✅ WITHDRAW (BY ACCOUNT NUMBER)
    @PostMapping("/{accountNumber}/withdraw")
    public ResponseEntity<String> withdraw(
            @PathVariable String accountNumber,
            @RequestBody MoneyRequest req) {

        return ResponseEntity.ok(
                "✅ New Balance: ₹" +
                        service.withdraw(accountNumber, req.getAmount())
        );
    }

    // ✅ MINI STATEMENT (PDF DOWNLOAD)
    @GetMapping("/{accountNumber}/mini-statement")
    public void downloadMiniStatement(
            @PathVariable String accountNumber,
            HttpServletResponse response) throws Exception {

        List<Transaction> transactions =
                service.getMiniStatement(accountNumber);

        response.setContentType("application/pdf");
        response.setHeader(
                "Content-Disposition",
                "attachment; filename=mini-statement.pdf"
        );

        com.itextpdf.text.Document pdf =
                new com.itextpdf.text.Document();

        PdfWriter.getInstance(pdf,
                response.getOutputStream());

        pdf.open();

        pdf.add(new Paragraph("ATM Mini Statement"));
        pdf.add(new Paragraph("Account Number: " + accountNumber));
        pdf.add(new Paragraph("Generated On: " + LocalDateTime.now()));
        pdf.add(new Paragraph("--------------------------------------------------"));

        for (Transaction t : transactions) {
            pdf.add(new Paragraph(
                    t.getTimestamp() + " | " +
                            t.getType() + " | ₹" +
                            t.getAmount() + " | " +
                            t.getDescription()
            ));
        }

        pdf.close();
    }
}

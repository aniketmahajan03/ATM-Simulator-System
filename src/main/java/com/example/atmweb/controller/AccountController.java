package com.example.atmweb.controller;

import java.math.BigDecimal;
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

    // ✅ CHECK BALANCE (RETURN ONLY NUMBER)
@GetMapping("/balance/{accountNumber}")
public ResponseEntity<BigDecimal> checkBalance(
        @PathVariable String accountNumber) {

    return ResponseEntity.ok(
            service.checkBalance(accountNumber)
    );
}


   // ✅ DEPOSIT (RETURN ONLY NUMBER)
@PostMapping("/{accountNumber}/deposit")
public ResponseEntity<BigDecimal> deposit(
        @PathVariable String accountNumber,
        @RequestBody MoneyRequest req) {

    BigDecimal newBalance =
            service.deposit(accountNumber, req.getAmount());

    return ResponseEntity.ok(newBalance);
}


    // ✅ WITHDRAW (RETURN ONLY NUMBER)
@PostMapping("/{accountNumber}/withdraw")
public ResponseEntity<BigDecimal> withdraw(
        @PathVariable String accountNumber,
        @RequestBody MoneyRequest req) {

    BigDecimal newBalance =
            service.withdraw(accountNumber, req.getAmount());

    return ResponseEntity.ok(newBalance);
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

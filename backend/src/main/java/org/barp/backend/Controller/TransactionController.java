package org.barp.backend.Controller;

import org.barp.backend.Model.Transaction;
import org.barp.backend.Service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:3000"}, allowCredentials = "true")
@RestController
public class TransactionController {
    TransactionService TransactionService;

    @Autowired
    TransactionController(TransactionService TransactionService){this.TransactionService = TransactionService;}
    @GetMapping("Transaction")
    public ResponseEntity<List<Transaction>> getAllTransactions() { return this.TransactionService.getAllTransactions(); }
    @PostMapping("Transaction")
    public ResponseEntity<?> addNewTransaction(@RequestBody Transaction Transaction) { return this.TransactionService.addTransaction(Transaction); }

    @PostMapping("Transaction/deposit")
    public ResponseEntity<?> newDeposit(@RequestBody Transaction transaction) { return this.TransactionService.newDeposit(transaction); }
    @PostMapping("Transaction/withdraw")
    public ResponseEntity<?> newWithdrawal(@RequestBody Transaction transaction) { return this.TransactionService.newWithdrawal(transaction); }

}

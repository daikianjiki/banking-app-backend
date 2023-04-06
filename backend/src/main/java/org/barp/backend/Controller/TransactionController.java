package org.barp.backend.Controller;

import org.barp.backend.Model.Transaction;
import org.barp.backend.Service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = {"*"}, allowCredentials = "true")
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

    @GetMapping("Transaction/user")
    public ResponseEntity<?> getTransactionsByUserId(@RequestHeader Map<String, String> headers){
        long userId;

        try {
            userId = Long.parseLong(headers.get("userId"));
        } catch (NumberFormatException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid user id");
        }
        return this.TransactionService.findTransactionsByUserId(userId);
    }
}

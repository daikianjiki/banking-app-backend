package org.barp.backend.Controller;

import org.barp.backend.Model.MoneyAccount;
import org.barp.backend.Service.MoneyAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:3000"}, allowCredentials = "true")
@RestController
public class MoneyAccountController {
    MoneyAccountService MoneyAccountService;

    @Autowired
    MoneyAccountController(MoneyAccountService MoneyAccountService){this.MoneyAccountService = MoneyAccountService;}
    @GetMapping("account")
    public ResponseEntity<List<MoneyAccount>> getAllMoneyAccounts() { return this.MoneyAccountService.getAllMoneyAccounts(); }
    @PostMapping("account")
    public ResponseEntity<?> addNewMoneyAccount(@RequestBody MoneyAccount MoneyAccount) { return this.MoneyAccountService.addMoneyAccount(MoneyAccount); }
}

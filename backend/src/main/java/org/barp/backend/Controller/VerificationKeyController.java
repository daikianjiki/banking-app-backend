package org.barp.backend.Controller;

import org.barp.backend.Model.VerificationKey;
import org.barp.backend.Service.VerificationKeyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"*"})

@RestController
public class VerificationKeyController {
    VerificationKeyService VerificationKeyService;

    @Autowired
    VerificationKeyController(VerificationKeyService VerificationKeyService){this.VerificationKeyService = VerificationKeyService;}
    @GetMapping("VerificationKey")
    public ResponseEntity<List<VerificationKey>> getAllVerificationKeys() { return this.VerificationKeyService.getAllVerificationKeys(); }
    @PostMapping("VerificationKey")
    public ResponseEntity<?> addNewVerificationKey(@RequestBody VerificationKey VerificationKey) { return this.VerificationKeyService.addVerificationKey(VerificationKey); }
}

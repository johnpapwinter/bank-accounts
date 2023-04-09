package com.bank_accounts.controller;

import com.bank_accounts.domain.exceptions.HolderDoesNotExistException;
import com.bank_accounts.domain.entities.Holder;
import com.bank_accounts.service.HolderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class HolderController {

    private final HolderServiceImpl holderService;

    @Autowired
    public HolderController(HolderServiceImpl holderService) {
        this.holderService = holderService;
    }

    @GetMapping("/holder/{ssn}")
    public ResponseEntity<Holder> getHolder(@PathVariable("ssn") String ssn) {
        Optional<Holder> readHolder = holderService.readHolder(ssn);
        return readHolder.map(holder -> new ResponseEntity<>(holder, HttpStatus.OK))
                .orElseThrow(() -> new HolderDoesNotExistException());
    }

    @GetMapping("/holder")
    public ResponseEntity<List<Holder>> getAllHolders() {
        return new ResponseEntity<>(holderService.readAllHolders(), HttpStatus.OK);
    }

    @PostMapping("/holder")
    public ResponseEntity<Holder> addHolder(@RequestBody Holder holder) {
        holderService.createHolder(holder);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/holder/{ssn}")
    public ResponseEntity<Holder> updateHolder(@PathVariable("ssn") String ssn, @RequestBody Holder holder) {
        holderService.updateHolder(ssn, holder);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/holder/{ssn}")
    public ResponseEntity<Holder> deleteHolder(@PathVariable("ssn") String ssn) {
        holderService.deleteHolder(ssn);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/holder/{ssn}/{iban}")
    public ResponseEntity<Holder> addAccountToHolder(@PathVariable("ssn") String ssn, @PathVariable("iban") String iban) {
        holderService.addAccount(iban, ssn);
        return new ResponseEntity<>(HttpStatus.OK);
    }



}

package com.bank_accounts.controller;

import com.bank_accounts.model.Holder;
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
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }

    @GetMapping("/holder")
    public ResponseEntity<List<Holder>> getAllHolders() {
        return new ResponseEntity<>(holderService.readAllHolders(), HttpStatus.OK);
    }

    @PostMapping("/holder")
    public ResponseEntity<Holder> addHolder(@RequestBody Holder holder) {
        boolean added = holderService.createHolder(holder);
        if (!added) {
            return new ResponseEntity<>(holder, HttpStatus.ALREADY_REPORTED);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/holder/{ssn}")
    public ResponseEntity<Holder> updateHolder(@PathVariable("ssn") String ssn, @RequestBody Holder holder) {
        boolean updated = holderService.updateHolder(ssn, holder);
        if(!updated) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/holder/{ssn}")
    public ResponseEntity<Holder> deleteHolder(@PathVariable("ssn") String ssn) {
        boolean deleted = holderService.deleteHolder(ssn);
        if (!deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/holder/{ssn}/{iban}")
    public ResponseEntity<Holder> addAccountToHolder(@PathVariable("ssn") String ssn, @PathVariable("iban") String iban) {
        holderService.addAccount(iban, ssn);
        return new ResponseEntity<>(HttpStatus.OK);
    }



}

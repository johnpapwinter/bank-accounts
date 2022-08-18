package com.bank_accounts.controller;

import com.bank_accounts.model.Holder;
import com.bank_accounts.service.HolderService;
import com.bank_accounts.service.exceptions.EntityNotFoundException;
import com.bank_accounts.service.exceptions.EntryAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class HolderController {

    private final HolderService holderService;

    public HolderController(HolderService holderService) {
        this.holderService = holderService;
    }

    @GetMapping("/holder/{ssn}")
    public ResponseEntity<Holder> getHolder(@PathVariable("ssn") String ssn) {
        Optional<Holder> readHolder = holderService.readHolder(ssn);
        if (readHolder.isPresent()) {
            return new ResponseEntity<>(readHolder.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/holder")
    public ResponseEntity<List<Holder>> getAllHolders() {
        return new ResponseEntity<>(holderService.readAllHolders(), HttpStatus.OK);
    }

    @PostMapping("/holder")
    public ResponseEntity<Holder> addHolder(@RequestBody Holder holder) throws EntryAlreadyExistsException {
        holderService.createHolder(holder);
        return new ResponseEntity<>(holder, HttpStatus.CREATED);
    }

    @PutMapping("/holder/{ssn}")
    public ResponseEntity<Holder> updateHolder(@PathVariable("ssn") String ssn, @RequestBody Holder holder) {
        holderService.updateHolder(ssn, holder);
        return new ResponseEntity<>(holder, HttpStatus.OK);
    }

    @DeleteMapping("/holder/{ssn}")
    public ResponseEntity<Holder> deleteHolder(@PathVariable("ssn") String ssn) throws EntityNotFoundException {
        holderService.deleteHolder(ssn);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}

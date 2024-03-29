package com.bank_accounts.controller;

import com.bank_accounts.domain.dto.HolderDTO;
import com.bank_accounts.service.HolderService;
import com.bank_accounts.service.HolderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/holder")
public class HolderController {

    private final HolderService holderService;

    @Autowired
    public HolderController(HolderService holderService) {
        this.holderService = holderService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<HolderDTO> getHolder(@PathVariable Long id) {
        HolderDTO response = holderService.getHolderById(id);

        return ResponseEntity.ok().body(response);
    }

    @GetMapping
    public ResponseEntity<Page<HolderDTO>> getAllHolders(Pageable pageable) {
        Page<HolderDTO> response = holderService.getAllHolders(pageable);

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/all")
    public ResponseEntity<List<HolderDTO>> fetchAllHolders() {
        List<HolderDTO> response = holderService.fetchAllHolders();

        return ResponseEntity.ok().body(response);
    }

    @PostMapping
    public ResponseEntity<Void> addHolder(@RequestBody HolderDTO holderDTO) {
        holderService.createHolder(holderDTO);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateHolder(@PathVariable Long id, @RequestBody HolderDTO holderDTO) {
        holderService.updateHolder(id, holderDTO);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHolder(@PathVariable Long id) {
        holderService.deleteHolder(id);

        return ResponseEntity.ok().build();
    }



}

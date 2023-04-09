package com.bank_accounts.domain.entities;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "account")
@NoArgsConstructor
@Setter
@Getter
@ToString
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "iban", nullable = false)
    private String iban;

    @Column(name = "balance", nullable = false)
    private Double balance;

    @Column(name = "overdraft", nullable = false)
    private Boolean overdraft;

    @ManyToMany(mappedBy = "accounts", fetch = FetchType.LAZY)
    protected Set<Holder> holders = new HashSet<>();


    public Account(String iban, Double balance, Boolean overdraft) {
        this.iban = iban;
        this.balance = balance;
        this.overdraft = overdraft;
    }



}

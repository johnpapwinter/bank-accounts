package com.bank_accounts.model;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;
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
    protected Set<Holder> holders;


    public Account(String iban, Double balance, Boolean overdraft) {
        this.iban = iban;
        this.balance = balance;
        this.overdraft = overdraft;
    }

    public boolean addHolder(Holder holder) {
        if (holder == null) {
            return false;
        }
        holder.getAccounts().forEach(account -> {
            if (Objects.deepEquals(account, this)) {
                throw new RuntimeException();
            }
        });
        holder.getAccounts().add(this);
        this.holders.add(holder);
        return true;
    }

    public boolean removeHolder(Holder holder) {
        if (holder == null) {
            return false;
        }
        if(!holders.contains(this)) {
            return false;
        } else {
            this.holders.remove(holder);
            return true;
        }
    }




}

package com.bank_accounts.domain.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "account")
@NoArgsConstructor
@Setter
@Getter
public class Account implements Serializable {

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

    @Column(name = "date_opened", nullable = false)
    private LocalDateTime dateOpened;

    @ManyToMany(mappedBy = "accounts", fetch = FetchType.LAZY)
    private List<Holder> holders = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(id, account.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

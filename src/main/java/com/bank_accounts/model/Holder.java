package com.bank_accounts.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name="holder")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Holder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name="firstname", nullable = false)
    private String firstname;

    @Column(name="lastname", nullable = false)
    private String lastname;

    @Column(name="ssn", nullable = false, unique = true)
    private String ssn;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "holder_account", joinColumns = @JoinColumn(name = "holder_id", referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name = "account_id", referencedColumnName = "id"))
    @JsonIgnore
    protected Set<Account> accounts = new HashSet<>();


    public Holder(String firstName, String lastName, String ssn) {
        this.firstname = firstName;
        this.lastname = lastName;
        this.ssn = ssn;
    }

    public boolean addAccount(Account account) {
        if (account == null) {
            return false;
        }
        account.getHolders().forEach(holder -> {
            if (Objects.deepEquals(holder, this)) {
                throw new IllegalStateException();
            }
        });

        account.getHolders().add(this);
        this.accounts.add(account);
        return true;

    }

    public boolean removeAccount(Account account) {
        if(account == null) {
            return false;
        }
        if(!accounts.contains(account)) {
            throw new IllegalStateException();
        }
        this.accounts.remove(account);
        account.getHolders().remove(this);
        return true;
    }
}

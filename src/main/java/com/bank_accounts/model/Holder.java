package com.bank_accounts.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
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

    @ManyToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinTable(name = "holder_account", joinColumns = @JoinColumn(name = "holder_id", referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name = "account_id", referencedColumnName = "id"))
    @JsonIgnore
    protected Set<Account> accounts;


    public Holder(String firstName, String lastName, String ssn) {
        this.firstname = firstName;
        this.lastname = lastName;
        this.ssn = ssn;
    }

    public boolean addAccount(Account account) {
        if (account == null) {
            return false;
        }
//        account.getHolders().forEach(holder -> {
//            if (Objects.deepEquals(holder, this)) {
//                throw new RuntimeException();
//            }
//        });
        for (Holder holder : account.getHolders()) {
            if (Objects.deepEquals(holder, this)) {
                throw new RuntimeException();
            }
        }

        account.getHolders().add(this);
        this.accounts.add(account);
        return true;

    }

    public boolean removeAccount(Account account) {
        if(account == null) {
            return false;
        }
        if(!accounts.contains(this)) {
            return false;
        } else {
            this.accounts.remove(account);
            return true;
        }
    }
}

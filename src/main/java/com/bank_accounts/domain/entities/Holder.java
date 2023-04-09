package com.bank_accounts.domain.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name="holder")
@NoArgsConstructor
@Getter
@Setter
public class Holder implements Serializable {

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
    private List<Account> accounts = new ArrayList<>();


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Holder holder = (Holder) o;
        return Objects.equals(id, holder.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

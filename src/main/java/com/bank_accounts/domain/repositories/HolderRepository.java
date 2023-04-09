package com.bank_accounts.domain.repositories;

import com.bank_accounts.domain.entities.Holder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface HolderRepository extends JpaRepository<Holder, Long> {

    Optional<Holder> findBySsn(String ssn);

}

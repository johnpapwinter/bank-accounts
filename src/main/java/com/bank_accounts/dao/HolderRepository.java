package com.bank_accounts.dao;

import com.bank_accounts.model.Holder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface HolderRepository extends JpaRepository<Holder, Long> {

    Optional<Holder> findBySsn(String ssn);

}

package com.bank_accounts.service;

import com.bank_accounts.model.Holder;
import com.bank_accounts.service.exceptions.EntityNotFoundException;
import com.bank_accounts.service.exceptions.EntryAlreadyExistsException;

import java.util.List;
import java.util.Optional;

public interface IHolderService {

    boolean createHolder(Holder newHolder);

    Optional<Holder> readHolder(String ssn);

    List<Holder> readAllHolders();

    boolean updateHolder(String ssn, Holder updatedHolder);

    boolean deleteHolder(String ssn);

}

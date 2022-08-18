package com.bank_accounts.service;

import com.bank_accounts.model.Holder;
import com.bank_accounts.service.exceptions.EntityNotFoundException;
import com.bank_accounts.service.exceptions.EntryAlreadyExistsException;

import java.util.List;
import java.util.Optional;

public interface IHolderService {

    int createHolder(Holder newHolder) throws EntryAlreadyExistsException, EntityNotFoundException;

    Optional<Holder> readHolder(String ssn) throws EntityNotFoundException;

    List<Holder> readAllHolders();

    int updateHolder(String ssn, Holder updatedHolder) throws EntityNotFoundException;

    int deleteHolder(String ssn) throws EntityNotFoundException;

}

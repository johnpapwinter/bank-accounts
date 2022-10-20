package com.bank_accounts.service;

import com.bank_accounts.exceptions.AccountDoesNotExistException;
import com.bank_accounts.exceptions.HolderAlreadyExistsException;
import com.bank_accounts.exceptions.HolderDoesNotExistException;
import com.bank_accounts.exceptions.NoHoldersExistException;
import com.bank_accounts.repositories.AccountRepository;
import com.bank_accounts.repositories.HolderRepository;
import com.bank_accounts.model.Account;
import com.bank_accounts.model.Holder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HolderServiceImpl implements HolderService {

    private final HolderRepository holderRepository;

    private final AccountServiceImpl accountService;

    private final AccountRepository accountRepository;


    @Autowired
    public HolderServiceImpl(HolderRepository holderRepository, AccountServiceImpl accountService, AccountRepository accountRepository) {
        this.holderRepository = holderRepository;
        this.accountService = accountService;
        this.accountRepository = accountRepository;
    }


    @Override
    public boolean createHolder(Holder newHolder) {
        Optional<Holder> createdHolder = readHolder(newHolder.getSsn());
        if (createdHolder.isPresent()) {
            throw new HolderAlreadyExistsException();
        }
        holderRepository.save(newHolder);
        return true;
    }


    @Override
    public Optional<Holder> readHolder(String ssn) {
        return holderRepository.findBySsn(ssn);
    }


    @Override
    public List<Holder> readAllHolders() {
        List<Holder> holders = holderRepository.findAll();
        if (!holders.isEmpty()) {
            return holders;
        } else {
            throw new NoHoldersExistException();
        }
    }


    @Override
    public boolean updateHolder(String ssn, Holder updatedHolder) {
        Optional<Holder> holder = readHolder(ssn);
        if (holder.isEmpty()) {
            throw new HolderDoesNotExistException();
        }
        updatedHolder.setId(holder.get().getId());
        holderRepository.save(updatedHolder);
        return true;
    }


    @Override
    public boolean deleteHolder(String ssn) {
        Optional<Holder> deletedHolder = readHolder(ssn);
        if (deletedHolder.isEmpty()) {
            throw new HolderDoesNotExistException();
        }
        holderRepository.deleteById(deletedHolder.get().getId());
        return true;
    }

    public void addAccount(String iban, String ssn) {
        Optional<Holder> holder = readHolder(ssn);
        if(holder.isEmpty()) {
            throw new HolderDoesNotExistException();
        }
        Optional<Account> account = accountService.readAccountInfo(iban);
        if(account.isEmpty()) {
            throw new AccountDoesNotExistException();
        }
        holder.get().addAccount(account.get());
        holderRepository.save(holder.get());
        accountRepository.save(account.get());
    }
}

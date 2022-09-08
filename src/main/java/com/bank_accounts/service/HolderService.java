package com.bank_accounts.service;

import com.bank_accounts.repositories.AccountRepository;
import com.bank_accounts.repositories.HolderRepository;
import com.bank_accounts.model.Account;
import com.bank_accounts.model.Holder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HolderService implements IHolderService {

    @Autowired
    private final HolderRepository holderRepository;

    @Autowired
    private final AccountService accountService;

    @Autowired
    private final AccountRepository accountRepository;


    @Autowired
    public HolderService(HolderRepository holderRepository, AccountService accountService, AccountRepository accountRepository) {
        this.holderRepository = holderRepository;
        this.accountService = accountService;
        this.accountRepository = accountRepository;
    }


    @Override
    public boolean createHolder(Holder newHolder) {
        Optional<Holder> createdHolder = readHolder(newHolder.getSsn());
        if (createdHolder.isPresent()) {
            return false;
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
            throw new IllegalStateException();
        }
    }


    @Override
    public boolean updateHolder(String ssn, Holder updatedHolder) {
        Optional<Holder> holder = readHolder(ssn);
        if (holder.isEmpty()) {
            return false;
        }
        updatedHolder.setId(holder.get().getId());
        holderRepository.save(updatedHolder);
        return true;
    }


    @Override
    public boolean deleteHolder(String ssn) {
        Optional<Holder> deletedHolder = readHolder(ssn);
        if (deletedHolder.isEmpty()) {
            return false;
        }
        holderRepository.deleteById(deletedHolder.get().getId());
        return true;
    }

    public void addAccount(String iban, String ssn) {
        Optional<Holder> holder = readHolder(ssn);
        if(holder.isEmpty()) {
            throw new IllegalStateException();
        }
        Optional<Account> account = accountService.readAccountInfo(iban);
        if(account.isEmpty()) {
            throw new IllegalStateException();
        }
        holder.get().addAccount(account.get());
        holderRepository.save(holder.get());
        accountRepository.save(account.get());
    }
}

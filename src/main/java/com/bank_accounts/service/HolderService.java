package com.bank_accounts.service;

import com.bank_accounts.dao.AccountRepository;
import com.bank_accounts.dao.HolderRepository;
import com.bank_accounts.model.Account;
import com.bank_accounts.model.Holder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HolderService implements IHolderService {

    private final HolderRepository holderRepository;

    private final AccountRepository accountRepository;


    public HolderService(HolderRepository holderRepository, AccountRepository accountRepository) {
        this.holderRepository = holderRepository;
        this.accountRepository = accountRepository;
    }


    @Override
    public boolean createHolder(Holder newHolder) {
        if (readHolder(newHolder.getSsn()).isPresent()) {
            throw new IllegalStateException();
        }
        holderRepository.save(newHolder);
        return true;
    }


    @Override
    public Optional<Holder> readHolder(String ssn) {
        Optional<Holder> foundHolder = holderRepository.findBySsn(ssn);
        if (foundHolder.isPresent()) {
            return foundHolder;
        } else {
            throw new IllegalStateException();
        }
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
        updatedHolder.setId(holder.get().getId());
        holderRepository.save(updatedHolder);
        return true;
    }


    @Override
    public boolean deleteHolder(String ssn) {
        Optional<Holder> deletedHolder = readHolder(ssn);
        holderRepository.deleteById(deletedHolder.get().getId());
        return true;
    }

    public void addAccount(Account account, String ssn) {
        Optional<Holder> holder = readHolder(ssn);
        holder.get().addAccount(account);
        holderRepository.save(holder.get());
        accountRepository.save(account);
    }
}

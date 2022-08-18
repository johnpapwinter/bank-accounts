package com.bank_accounts.service;

import com.bank_accounts.dao.HolderRepository;
import com.bank_accounts.model.Account;
import com.bank_accounts.model.Holder;
import com.bank_accounts.service.exceptions.EntityNotFoundException;
import com.bank_accounts.service.exceptions.EntryAlreadyExistsException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HolderService implements IHolderService {

    private final HolderRepository holderRepository;


    public HolderService(HolderRepository holderRepository) {
        this.holderRepository = holderRepository;
    }


    @Override
    public int createHolder(Holder newHolder) throws EntryAlreadyExistsException {
        if (readHolder(newHolder.getSsn()).isPresent()) {
            throw new EntryAlreadyExistsException(newHolder.getSsn());
        }
        Holder holder = new Holder(newHolder.getFirstname(), newHolder.getLastname(), newHolder.getSsn());
        holderRepository.save(holder);
        return 0;
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
            // TODO new Exception there are no Holders
            throw new RuntimeException();
        }
    }


    @Override
    public int updateHolder(String ssn, Holder updatedHolder) {
        Optional<Holder> holder = readHolder(ssn);
        updatedHolder.setId(holder.get().getId());
        holderRepository.save(updatedHolder);
        return 0;
    }


    @Override
    public int deleteHolder(String ssn) throws EntityNotFoundException {
        Optional<Holder> deletedHolder = readHolder(ssn);
        holderRepository.deleteById(deletedHolder.get().getId());
        return 0;
    }

    public void addAccount(Account account, String ssn) throws EntityNotFoundException {
        Optional<Holder> holder = readHolder(ssn);
        holder.get().addAccount(account);
        holderRepository.save(holder.get());
    }
}
